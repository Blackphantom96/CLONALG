import random

class Antigen(object):
    def __init__(self, length, data):
        self.data = data

    def __repr__(self):
        return str(self.data)
