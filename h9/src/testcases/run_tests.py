import os
import sys
import filecmp

test_cases = {
	200: ['200_0_0', '200_100.00_0', '200_-50.9_0.5', '200_50.55_50.55'],
	300: ['300_0_0', '300_100.00_0', '300_-50.9_0.5', '300_50.55_50.55'],
	400: ['400_1e-7_5', '400_1e-8_5', '400_1e-9_5', '400_1e-5_5'],
	600: ['600_500'],
	700: ['700_0.1_5', '700_1_5', '700_0.01_5']
}

def run_all():
	passed = True
	for part in test_cases:
		passed = run(part) and passed

	if passed:
		print()
		print('All sample tests passed.')
		print('REMEMBER: This doesn\'t guarantee that your program is correct in all the scenarios.')


def run(part):
	passed = True

	for test_case in test_cases[part]:
		if os.path.isfile(test_case + '.out'):
			if not filecmp.cmp(test_case + '.exp', test_case + '.out', shallow=False):
				passed = False
				print('Test case', test_case, 'failed')
		else:
			print('Plese provide sample output for', test_case)
			passed = False

	if passed:
		print('Tests passed for part', part)
	
	return passed

if __name__ == "__main__":
	if len(sys.argv) == 1:
		run_all()
	else:
		for part in sys.argv[1:]:
			run(int(part))
