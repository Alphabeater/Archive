numbers = ['one', 'two', 'three', 'four', 'five', 'six', 'seven', 'eight', 'nine']
s = 0

for line in open("input.txt"):
    for i, left in enumerate(line):
        break_outer_loop = False
        if left.isdigit():
            first_digit = int(left)
            break
        for number in numbers:
            n = line[i:i + len(number)]
            if n in numbers:
                first_digit = numbers.index(n) + 1
                break_outer_loop = True  # if number found breaks the outer loop
                break
        if break_outer_loop:
            break

    for j, right in enumerate(reversed(line)):
        break_outer_loop = False
        if right.isdigit():
            second_digit = int(right)
            break
        for number in numbers:
            n = line[len(line) - j - len(number):len(line) - j]
            if n in numbers:
                second_digit = numbers.index(n) + 1
                break_outer_loop = True  # if number found breaks the outer loop
                break
        if break_outer_loop:
            break

    line_sum = first_digit * 10 + second_digit  # every line guarantees having at least 1 number
    s += line_sum

print(s)