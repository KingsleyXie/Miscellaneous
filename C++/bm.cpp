#include <bits/stdc++.h>
using namespace std;

void print(const vector<int> &v) {
	cout << "[ ";
	for (int i : v) cout << i << " ";
	cout << "]\n";
}

bool is_prefix(const string &p, int start) {
	int lenp = p.length();
	for (int i = 0; i < lenp - start; ++i) {
		if (p[i] != p[i + start]) return false;
	}
	return true;
}

int suffix_len(const string &p, int end) {
	int lenp = p.length(), i;
	for (i = 0; p[lenp - 1 - i] == p[end - i] && i < end; ++i);
	return i;
}

vector<int> bm(const string &s, const string &p) {
	cout << s << " -> " << p << " -> ";
	int lens = s.length(), lenp = p.length();
	vector<int> ans;
	if (!(lens && lenp)) return ans;

	vector<int> bc(256, lenp); // bad character
	for (int i = 0; i < lenp - 1; ++i)
		bc[p[i]] = lenp - 1 - i;

	vector<int> gs(lenp, 0); // good suffix
	for (int i = lenp - 1, last = 0; i > 0; --i) {
		if (is_prefix(p, i)) last = i;
		gs[i -  1] = last;
	}
	for (int i = 0, len; i < lenp; ++i) {
		len = suffix_len(p, i);
		if (len != 0 && p[i - len] != p[lenp - 1 - len]) {
			gs[lenp - 1 - len] = lenp - 1 - i;
		}
	}

	int offset = 0, i;
	while (offset + lenp <= lens) {
		for (i = lenp - 1; i >= 0 && p[i] == s[offset + i]; --i);
		if (i < 0) {
			ans.push_back(offset);
			offset++;
		} else {
			offset += max(bc[s[offset + lenp - 1]], gs[i]);
		}
	}
	return ans;
}

int main()
{
	print(bm("HERE IS A SIMPLE EXAMPLE", "EXAMPLE"));
	print(bm("THIS IS A TEST TEXT", "TEST"));
	print(bm("AABAACAADAABAABA", "AABA"));
	print(bm("abcdefghijklmn", "aab"));
	print(bm("abcabcacbabc", "abc"));
	print(bm("abcabcacbabc", "abcabcacbabc"));
	print(bm("AAAAAAAAAAAAAAAAAA", "AAAAA"));
	print(bm("aa", "a"));
	print(bm("a", "a"));
	print(bm("", "a"));
	print(bm("a", ""));
	return 0;
}
