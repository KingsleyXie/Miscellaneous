#include <bits/stdc++.h>
using namespace std;

void print(const vector<int> &v) {
	cout << "[ ";
	for (int i : v) cout << i << " ";
	cout << "]\n";
}

vector<int> kmp(const string &s, const string &p) {
	cout << s << " -> " << p << " -> ";
	int lens = s.length(), lenp = p.length();
	vector<int> ans;
	if (!lenp) return vector<int>{0};
	if (!lens) return vector<int>{-1};

	vector<int> next(lenp);
	for (int i = 1, k = 0; i < lenp; ++i) {
		while (k && p[k] != p[i]) k = next[k - 1];
		if (p[k] == p[i]) ++k;
		next[i] = k;
	}
	
	for (int i = 0, k = 0; i < lens; ++i) {
		while (k && p[k] != s[i]) k = next[k - 1];
		if (p[k] == s[i]) ++k;
		if (k == lenp) {
			ans.push_back(i - lenp + 1);
			k = next[lenp - 1];
		}
	}
	return ans;
}

int main()
{
	print(kmp("HERE IS A SIMPLE EXAMPLE", "EXAMPLE"));
	print(kmp("THIS IS A TEST TEXT", "TEST"));
	print(kmp("AABAACAADAABAABA", "AABA"));
	print(kmp("abcdefghijklmn", "aab"));
	print(kmp("abcabcacbabc", "abc"));
	print(kmp("abcabcacbabc", "abcabcacbabc"));
	print(kmp("AAAAAAAAAAAAAAAAAA", "AAAAA"));
	print(kmp("aa", "a"));
	print(kmp("a", "a"));
	print(kmp("", "a"));
	print(kmp("a", ""));
	return 0;
}
