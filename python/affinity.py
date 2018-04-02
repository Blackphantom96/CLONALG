def calc_affinity(ag, ab):
    d = 0

    for i in range(len(ab.data)):
        d += 1 if ab.data[i] != ag.data[i] else 0
        
    return d
    
