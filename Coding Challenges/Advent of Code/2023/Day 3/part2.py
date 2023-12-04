symbols = ['*', '#', '-', '+', '@', '=', '&', '%', '$', '/', '!', '.']
lines = []
s = 0

for line in open("input.txt"):
    lines.append(line.strip())


def check_gear(i, j):
    gears = []

    for m in range(i - 1, i + 2):
        if not (0 <= m < len(lines)):
            continue
        for n in range(j - 1, j + 2):
            if not (0 <= n < len(lines[m])):
                continue
            if lines[m][n].isdigit():  # get whole number avoiding overflows
                start_index = n
                while lines[m][start_index - 1] not in symbols:
                    start_index -= 1
                    if start_index == 0:
                        break

                end_index = n
                while lines[m][end_index + 1] not in symbols:
                    end_index += 1
                    if end_index == len(lines[0]) - 1:
                        break

                gears.append(int(lines[m][start_index:end_index + 1]))

                if lines[m][j].isdigit():  # avoid rescanning the same number
                    break

    if len(gears) == 2:  # only gears that contains 2 adjacent numbers
        # print(gears)  # for debugging purposes
        return gears[0] * gears[1]
    return 0


for i, line in enumerate(lines):
    for j, c in enumerate(line):
        if c == '*':
            # print(f"Line {i + 1}, Indices: ({i}, {j}), sum:{s}")  # for debugging purposes
            s += check_gear(i, j)

print(s)
