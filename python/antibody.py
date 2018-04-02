import random

class Antibody():
    def __init__(self, length, data = None):
        assert length >= 0
        if (data is not None):
            assert all(isinstance(e, bool) for e in data)
            self.data = data
        else:
            self.data = [random.random() for i in range(length)]

        self.aff = float('inf')

    def __repr__(self):
        return "<data: {}, aff: {}>".format(self.data, self.aff)

    def __str__(self):
        return "<data: {}, aff: {}>".format(self.data, self.aff)
