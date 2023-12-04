s = 0


def find_num(f, r):
    mid = (r + f) // 2
    if f == r and x != w[mid]:
        return False
    if x > w[mid]:
        return find_num(mid + 1, r)
    elif x < w[mid]:
        return find_num(f, mid)
    else:
        return True


for i, line in enumerate(open("input.txt")):
    ind = line.find(':')
    w, m = [sorted(map(int, nums.split())) for nums in line[ind + 1:].strip().split('|')]
    ls = -1

    for x in m:
        if find_num(0, len(w) - 1):
            ls += 1
        # print(f"found {x} in {w}?: {find_num(0, len(w) - 1)}") # for debugging purposes
    if ls != -1:
        s += 2 ** ls

print(s)
