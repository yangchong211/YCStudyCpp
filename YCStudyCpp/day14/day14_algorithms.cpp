//
// Created by 杨充 on 2024/6/21.
//

#include <iostream>
#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

//之前一些编译器使用 C++11 的编译参数是 -std=c++11
//g++ -std=c++11 day14_algorithms.cpp

//14.2.1.1 find：在容器中查找指定元素
void test14_2_1_1();
//14.2.1.2 count：计算容器中指定元素的个数
void test14_2_1_2();



int main() {
    test14_2_1_1();
    test14_2_1_2();
    return 0;
}

//14.2.1.1 find：在容器中查找指定元素
void test14_2_1_1() {
    std::cout << "14.2.1.1 find：在容器中查找指定元素" << std::endl;
    //在C++的STL中，find算法用于在容器中查找指定元素的位置。它返回一个迭代器，指向第一个匹配的元素，如果没有找到匹配的元素，则返回容器的end()迭代器。
    std::vector<int> numbers = {5, 2, 8, 1, 9};
    auto it = std::find(numbers.begin(),numbers.end(),8);
    if (it != numbers.end()) {
        //我们使用std::find算法在向量numbers中查找元素为8的位置。
        //如果找到了匹配的元素，我们使用std::distance函数计算迭代器的位置，并输出结果。如果没有找到匹配的元素，则输出未找到的消息。
        std::cout << "元素 8 在向量中的位置为：" << std::distance(numbers.begin(), it) << std::endl;
    } else {
        std::cout << "未找到元素 8" << std::endl;
    }
    //请注意，find算法在容器中进行线性搜索，因此它的时间复杂度为O(n)，其中n是容器中的元素数量。
    //如果需要在有序容器中进行查找，可以考虑使用std::binary_search算法或者使用有序容器的lower_bound和upper_bound函数。


    //元素 8 在向量中的位置为：2
}

//14.2.1.2 count：计算容器中指定元素的个数
void test14_2_1_2() {

}








