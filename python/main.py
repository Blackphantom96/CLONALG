from clonalg import *
from image_reader import create_antigen
from antibody import Antibody

ANTIGEN_FILE_NAMES = [str(i) for i in range(10)]

def create_antigens():
    ag = []

    for file in ANTIGEN_FILE_NAMES:
        ag.append(create_antigen(file))

    return ag

L = 120
N = 15
GEN = 2
n = 15
d = 2
beta = 10

def main():
    ag = create_antigens()
    ab = [Antibody(L) for i in range(N)]

    for g in ag:
        print_image(g.data)
    
    print("\n".join(compute(ag, ab, GEN, n, d, beta, L)))

if __name__ == "__main__":
    main()

