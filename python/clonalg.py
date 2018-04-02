from image_reader import *
from antibody import *
from antigen import *
import copy
import random

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

    ab.aff = d
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

def clone(ab, beta, N):
    res = []
    for i in range(len(ab)):
        n = 0
        for j in range(1, len(ab)+1):
            n += round((beta * N) / float(j))
        res.extend([copy.deepcopy(ab[i]) for _ in range(n)])
    return res

def mutate(ab):
    for i in range(len(ab)):
        num_mutations = int(min(len(ab[i].data), MUTATION_MULTIPLIER * ab[i].aff))

        length = len(ab[i].data)
        data = ab[i].data

        index = 0
        while num_mutations > 0:
            index = random.randint(0, length-1)
            data[index] = not data[index]
            num_mutations -= 1
def replace(abd, abr, d, L, abm):
    assert d < len(abr)
    
    abr.sort(key=lambda x: x.aff)
    if d > 0:
        for i in range(len(abr)-1, -1, -1):
            if (abr[i] not in abm and d > 0):
                abr[i] = Antibody(L)
                d -= 1
    if d > 0:
        abr.extend([Antibody(L) for _ in range(d)])

def generate(d, L):
    return [Antibody(L) for _ in range(d)]

def compute(ag,ab,gen,n,d,beta, L):
    # print(ag,ab,gen,n,d,beta, L)

    d = min(d, len(ab) - len(ag))

    abm = random.sample(ab, len(ag))
    for g in range(gen):
        print("Generation", g)
        for j, antigen in enumerate(ag):
            affinity(ab, ag[j])
            ab.sort(key=lambda x: x.aff)
            selected = select(ab, n)
            C = clone(selected, beta, len(ab))
            print(len(C))
            mutate(C)
            print(g)
            affinity(C, ag[j])
            C.sort(key=lambda x: x.aff)
            best_ab = select(C, 1)[0]
            if best_ab.aff < abm[j].aff:
                abm[j] = best_ab
            replace(abm, ab, d, L, abm)
    # print(abm)
    return abm
