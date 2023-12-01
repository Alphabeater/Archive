s = 0

for line in open("input.txt"):
    for left in line:
        if left.isdigit():
            first_digit = int(left)
            break
    for right in reversed(line):
        if right.isdigit():
            second_digit = int(right)
            break

    line_sum = first_digit * 10 + second_digit  # every line guarantees having at least 1 number
    s += line_sum

print(sum)
