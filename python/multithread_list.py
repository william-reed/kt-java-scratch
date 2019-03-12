from multiprocessing import Process, Manager
from time import sleep
import sys

def proc0(l):
    l.append('proc 0')
    sys.stdout.write('proc0: ')
    print(l)

def proc1(l):
    l.append('proc 1')
    sys.stdout.write('proc1: ')
    print(l)

if __name__ == "__main__":
    with Manager() as manager:
        l = manager.list()  # <-- can be shared between processes.
        processes = []
        processes.append(Process(target=proc0, args=(l,)))
        processes.append(Process(target=proc1, args=(l,)))

        for p in processes:
            p.start()

        for p in processes:
            p.join()

        print(l)

