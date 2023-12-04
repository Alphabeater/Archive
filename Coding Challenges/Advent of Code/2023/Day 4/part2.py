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


with open("input.txt", "r") as file:
    R = sum(1 for line in file)

acc = [1] * R

for i, line in enumerate(open("input.txt")):
    u = 0
    ind = line.find(':')
    w, m = [sorted(map(int, nums.split())) for nums in line[ind + 1:].strip().split('|')]

    for x in m:
        if find_num(0, len(w) - 1):
            u += 1
        # print(f"found {x} in {w}?: {find_num(0, len(w) - 1)}")  # for debugging purposes

    for j in range(u):
        acc[i + j + 1] += 1 * acc[i]
    # print(acc)  # for debugging purposes

print(sum(acc))
