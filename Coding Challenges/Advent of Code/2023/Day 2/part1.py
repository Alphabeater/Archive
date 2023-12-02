import re

s = 0
cubes = [12, 13, 14]  # red, green, and blue cubes (question to be answered)

for g, line in enumerate(open("input.txt")):
    if all(int(match) <= cubes[c] for c, color in enumerate(['red', 'green', 'blue']) for match
           in re.findall(r'(\d+) ' + color, line)):
        s += g + 1

print(s)
