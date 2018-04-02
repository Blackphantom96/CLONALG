from matplotlib.pylab import *
from antigen import Antigen

def create_antigen(name):
    image_matrix = imread('../src/images/' + name + '.png') # TODO
    data = []

    for row in image_matrix:
        data += [all(pixel) == 0 for pixel in row]

    return Antigen(len(data), data)
