import re

symbols = ['*', '#', '-', '+', '@', '=', '&', '%', '$', '/', '!']
lines = []
s = 0


def valid_part_num(i1, i2, j1, j2):  # scans the number also (which is not optimal)
    for i in range(i1, i2 + 1):
        if not (0 <= i < len(lines)):
            continue
        for j in range(j1, j2):
            if not (0 <= j < len(lines[i])):
                continue
            if lines[i][j] in symbols:
                return True
    return False


for line in open("input.txt"):
    lines.append(line.strip())

for i, line in enumerate(lines):
    for match in re.finditer(r'\d+', line):
        start, end = match.start(), match.end()
        num = match.group()

        if valid_part_num(i - 1, i + 1, start - 1, end + 1):
            print(f"Line {i + 1}, Match: {num}, Indices: ({start}, {end}), sum:{s}")  # for debugging purposes
            s += int(num)

        # line = line[:start] + ''.join(['.' for n in num]) + line[end:]  # for debugging purposes

print(s)
