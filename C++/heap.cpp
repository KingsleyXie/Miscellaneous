#include <bits/stdc++.h>

void heapify(std::vector<int> &vec, int i, int n)
{
    int l, r, m;
    while (1)
    {
        l = 2 * i + 1;
        r = 2 * i + 2;
        m = i;
        if (l <= n && vec[l] > vec[i]) m = l;
        if (r <= n && vec[r] > vec[m]) m = r;

        if (m != i)
        {
            std::swap(vec[i], vec[m]);
            i = m;
        }
        else
        {
            break;
        }
    }
}

void buildheap(std::vector<int> &vec, int n)
{
    for (int i = (n - 1) / 2; i >= 0; i--)
    {
        heapify(vec, i, n);
    }
}

int main()
{
    std::vector<int> vec = {4,1,3,2,16,9,10,14,8,7};
    buildheap(vec, vec.size() - 1);
}
