#include <bits/stdc++.h>
using namespace std;

void print(const vector<int> &v) {
	cout << "[ ";
	for (int i : v) cout << i << " ";
	cout << "]\n";
}

vector<int> bmh(const string &s, const string &p) {
	cout << s << " -> " << p << " -> ";
	int lens = s.length(), lenp = p.length();
	vector<int> ans;
	if (!(lens && lenp)) return ans;

	vector<int> bc(256, lenp); // bad character
	for (int i = 0; i < lenp - 1; ++i) {
		bc[p[i]] = lenp - 1 - i;
	}

	int offset = 0, i;
	while (offset + lenp <= lens) {
		for (i = lenp - 1; i >= 0 && p[i] == s[offset + i]; --i);
		if (i < 0) {
			ans.push_back(offset);
			offset++;
		} else {
			offset += bc[s[offset + lenp - 1]];
		}
	}
	return ans;
}

int main()
{
	print(bmh("HERE IS A SIMPLE EXAMPLE", "EXAMPLE"));
	print(bmh("THIS IS A TEST TEXT", "TEST"));
	print(bmh("AABAACAADAABAABA", "AABA"));
	print(bmh("abcdefghijklmn", "aab"));
	print(bmh("abcabcacbabc", "abc"));
	print(bmh("abcabcacbabc", "abcabcacbabc"));
	print(bmh("AAAAAAAAAAAAAAAAAA", "AAAAA"));
	print(bmh("aa", "a"));
	print(bmh("a", "a"));
	print(bmh("", "a"));
	print(bmh("a", ""));
	return 0;
}
