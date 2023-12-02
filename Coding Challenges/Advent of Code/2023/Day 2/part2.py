import re

s = 0
cubes = [12, 13, 14]  # red, green and blue cubes (question to be answered)

for g, line in enumerate(open("input.txt")):
    m = 1
    for c, color in enumerate(['red', 'green', 'blue']):
        m *= max([int(match) for match in re.findall(r'(\d+) ' + color, line)])
    s += m

print(s)
