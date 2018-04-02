from matplotlib import pylab
from antigen import Antigen

def create_antigen(name):
    image_matrix = pylab.imread('../src/images/' + name + '.png') # TODO
    data = []

    for row in image_matrix:
        data += [all(pixel) == 0 for pixel in row]

    return Antigen(len(data), data)
