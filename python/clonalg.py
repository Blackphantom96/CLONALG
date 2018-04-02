from image_reader import *
from antibody import *
from antigen import *
import copy
import random
import operator

MUTATION_MULTIPLIER = 1.0

def print_image(image):
    x = -1
    output = ''
    for i in range(12):
        for j in range(10):
            x += 1
            if image[x]:
                output += 'X'
            else:
                output += ' '
        output += '\n'
    print(output)

def calc_affinity(ag, ab):
    d = 0
    # print(ag, ab)
    for i in range(len(ab.data)):
        d += 1 if ab.data[i] != ag.data[i] else 0

#    ab.aff = d
    return d

def clone_antibody(ab, beta, n, aff_rank):
    n_c = int(round(beta * n / float(aff_rank)))

    return [copy.deepcopy(ab) for i in range(n_c)]

def mutate_antibody(ab, aff_rank):
    num_mut = min(aff_rank, len(ab.data))

    mut_keys = random.sample(range(len(ab.data)), num_mut)

    for key in mut_keys:
        ab.data[key] = not ab.data[key]

    return ab

def affinity(ab, ag):
    assert len(ab) > 0, "{}".format(ab)
    # print("ab", ab)
    for antibody in ab:
        calc_affinity(ag, antibody)

def select(ab, n):
    return ab[:n]


def compute(ag,ab,gen,n,d,beta, L):
    # print(ag,ab,gen,n,d,beta, L)
    abm = random.sample(ab, len(ag))
    d = min(d, len(ab) - len(ag))
    flag = False
    for g in range(gen):
        print("Generation", g)
        #print(abm)
        for j, antigen in enumerate(ag):
            #affinity(ab, antigen)
            for antibody in ab:
                antibody.aff = calc_affinity(antigen, antibody)
            ab.sort(key=lambda x: x.aff)
            selected = select(ab, n)
            affRank =0
            clones = []

            for a1 in selected:
                affRank += 1
                clones1 = clone_antibody(a1, beta, len(ab),affRank)
                for a2 in clones1:
                    clones.append(mutate_antibody(a2,affRank))
            #affinity(clones,antigen)
            for antibody in clones:
                antibody.aff = calc_affinity(antigen, antibody)
            #clones.sort(key=lambda antibody: antibody.aff)
            best = min(clones, key=operator.attrgetter('aff'))
            if best.aff < abm[j].maff:
                print(best.aff)
                old = abm[j]
                ab.remove(old)

                best.maff = best.aff
                ab.append(best)
                abm[j] = best
            if d > 0:
                ab.sort(key=lambda antibody: antibody.aff)
                num_to_kill = d
                for i in range(len(ab) - 1, -1, -1):
                    if num_to_kill == 0:
                        break

                    if ab[i] not in abm:
                        num_to_kill -= 1
                        del ab[i]
                ab = ab + [Antibody(L) for _ in range(d)]
    return abm
