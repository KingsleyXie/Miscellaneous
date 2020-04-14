#include <bits/stdc++.h>

int partition(std::vector<int> &vec, int low, int high)
{
    int pivot = vec[low];
    while (low < high)
    {
        while (low < high && vec[high] >= pivot) high--;
        vec[low] = vec[high];
        while (low < high && vec[low] <= pivot) low++;
        vec[high] = vec[low];
    }
    vec[low] = pivot;
    return low;
}

void quicksort(std::vector<int> &vec, int low, int high)
{
    if (low < high)
    {
        int pi = partition(vec, low, high);
        quicksort(vec, low, pi - 1);
        quicksort(vec, pi + 1, high);
    }
}

void nonrecqs(std::vector<int> &vec, int low, int high)
{
    std::stack<int> st;
    if (low < high)
    {
        int pi = partition(vec, low, high);
        if (low < pi - 1)
        {
            st.push(low);
            st.push(pi - 1);
        }
        if (pi + 1 < high)
        {
            st.push(pi + 1);
            st.push(high);
        }

        while (!st.empty())
        {
            int r = st.top(); st.pop();
            int l = st.top(); st.pop();
            pi = partition(vec, l, r);
            if (l < pi - 1)
            {
                st.push(l);
                st.push(pi - 1);
            }
            if (pi + 1 < r)
            {
                st.push(pi + 1);
                st.push(r);
            }
        }
    }
}

int main()
{
    std::vector<int> vec = {1, 4, 5, 7, 8, 9, 3, 2, 5, 6, 8, 5, 9};
    nonrecqs(vec, 0, vec.size() - 1);
    for (int i : vec) {
        std::cout << i << " ";
    }

    return 0;
}
