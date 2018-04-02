import random

class Antigen(object):
    def __init__(self, length, data):
        assert length > 0
        assert data is not None
        self.data = data

    def __repr__(self):
        return str(self.data)
