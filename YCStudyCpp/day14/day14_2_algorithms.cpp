//
// Created by 杨充 on 2024/6/21.
//

#include <iostream>
#include <iostream>
#include <vector>
#include <algorithm>
#include <numeric>

using namespace std;

//之前一些编译器使用 C++11 的编译参数是 -std=c++11
//g++ -std=c++11 day14_algorithms.cpp

//14.2.1.1 find：在容器中查找指定元素
void test14_2_1_1();
//14.2.1.2 count：计算容器中指定元素的个数
void test14_2_1_2();
//14.2.1.3 min_element和max_element
void test14_2_1_3();
//14.2.1.4 all_of、any_of和none_of
void test14_2_1_4();
//14.2.1.5 accumulate：计算容器中元素的累加和
void test14_2_1_5();
//14.2.1.6 equal：比较两个容器是否相等
void test14_2_1_6();
//14.2.1.7 find_if：在容器中查找满足特定条件的元素
void test14_2_1_7();


int main() {
    test14_2_1_1();
    test14_2_1_2();
    test14_2_1_3();
    test14_2_1_4();
    test14_2_1_5();
    test14_2_1_6();
    test14_2_1_7();
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
    std::cout << "14.2.1.2 count：计算容器中指定元素的个数" << std::endl;
    //在C++中，STL（Standard Template Library）提供了一个count算法，用于计算指定值在容器中出现的次数。
    //count算法可以应用于各种容器类型，如vector、list、array等。

    //创建了一个vector容器numbers，其中包含一些整数。然后，我们使用std::count算法来计算值为2的元素在容器中出现的次数。最后，我们将结果打印到控制台。
    std::vector<int> numbers = {1, 2, 3, 4, 2, 2, 5};
    //std::count算法接受三个参数：容器的起始迭代器、容器的结束迭代器和要计数的值。它返回指定值在容器中出现的次数。
    int count = std::count(numbers.begin(), numbers.end(), 2);
    //需要注意的是，std::count算法只适用于容器中的简单类型，如整数、浮点数等。
    //如果容器中存储的是自定义类型，你可能需要提供一个自定义的比较函数或使用其他算法来实现计数功能。
    std::cout << "Count: " << count << std::endl;
}


//14.2.1.3 min_element和max_element
void test14_2_1_3() {
    std::cout << "14.2.1.3 min_element和max_element" << std::endl;
    std::vector<int> numbers = {5, 2, 8, 1, 4};
//    int max = std::max_element(numbers.begin(),numbers.end());
//    int min = std::min_element(numbers.begin(),numbers.end());
    auto max = std::max_element(numbers.begin(),numbers.end());
    auto min = std::min_element(numbers.begin(),numbers.end());
    std::cout << "Min element: " << *min << std::endl;
    std::cout << "Max element: " << *max << std::endl;
}

//14.2.1.4 all_of、any_of和none_of
void test14_2_1_4() {
    std::cout << "14.2.1.4 all_of、any_of和none_of" << std::endl;
    //all_of算法：all_of算法用于检查容器中的所有元素是否都满足给定的条件。如果所有元素都满足条件，则返回true；否则返回false。
    std::vector<int> numbers1 = {1, 2, 3, 4, 5};
    bool allPositive = std::all_of(numbers1.begin(), numbers1.end(), [](int num) {
        return num > 0;
    });
    //使用std::all_of算法来检查numbers容器中的所有元素是否都大于0。
    //如果所有元素都大于0，则输出"All numbers are positive."；否则输出"Not all numbers are positive."。
    if (allPositive) {
        std::cout << "All numbers are positive." << std::endl;
    } else {
        std::cout << "Not all numbers are positive." << std::endl;
    }

    //any_of算法：any_of算法用于检查容器中的任意一个元素是否满足给定的条件。如果至少有一个元素满足条件，则返回true；否则返回false。
    std::vector<int> numbers2 = {1, 2, 3, 4, 5};
    bool hasNegative = std::any_of(numbers2.begin(), numbers2.end(), [](int num) {
        return num < 0;
    });
    //使用std::any_of算法来检查numbers容器中是否存在小于0的元素。
    //如果至少有一个元素小于0，则输出"At least one number is negative."；否则输出"No negative numbers found."。
    if (hasNegative) {
        std::cout << "At least one number is negative." << std::endl;
    } else {
        std::cout << "No negative numbers found." << std::endl;
    }


    //none_of算法：none_of算法用于检查容器中的所有元素是否都不满足给定的条件。如果所有元素都不满足条件，则返回true；否则返回false。
    std::vector<int> numbers3 = {1, 2, 3, 4, 5};
    bool noZero = std::none_of(numbers3.begin(), numbers3.end(), [](int num) {
        return num == 0;
    });
    //使用std::none_of算法来检查numbers容器中是否存在值为0的元素。
    //如果没有值为0的元素，则输出"No zero found."；否则输出"At least one zero found."。
    if (noZero) {
        std::cout << "No zero found." << std::endl;
    } else {
        std::cout << "At least one zero found." << std::endl;
    }
}


//14.2.1.5 accumulate：计算容器中元素的累加和
void test14_2_1_5() {
    std::cout << "14.2.1.5 accumulate：计算容器中元素的累加和" << std::endl;
    //在C++中，STL（Standard Template Library）提供了accumulate算法，用于对容器中的元素进行累加或累积操作。
    //
    //下面是accumulate算法的基本用法：
    std::vector<int> numbers = {1, 2, 3, 4, 5};
    auto sum = std::accumulate(numbers.begin(), numbers.end(), 0);
    std::cout << "Sum: " << sum << std::endl;
    //创建了一个vector容器numbers，其中包含一些整数。
    //然后，我们使用std::accumulate算法对容器中的元素进行累加操作，并将结果存储在变量sum中。
    //最后，我们将结果打印到控制台。
}

//14.2.1.6 equal：比较两个容器是否相等
void test14_2_1_6() {
    std::cout << "14.2.1.6 equal：比较两个容器是否相等" << std::endl;
    //示例中，我们创建了两个vector容器numbers1和numbers2，它们包含相同的整数序列。
    //然后，我们使用std::equal算法来比较两个容器是否相等。如果两个容器的元素相等，则返回true；否则返回false。
    std::vector<int> numbers1 = {1, 2, 3, 4, 5};
    std::vector<int> numbers2 = {1, 2, 3, 4, 5};
    bool isEqual = std::equal(numbers1.begin(), numbers1.end(), numbers2.begin());
    if (isEqual) {
        std::cout << "The two vectors are equal." << std::endl;
    } else {
        std::cout << "The two vectors are not equal." << std::endl;
    }
    //需要注意的是，std::equal算法要求两个容器的大小必须相等，否则将返回false。如果要比较容器的一部分元素，可以使用迭代器指定范围。
    //此外，std::equal算法还可以接受一个可选的二元操作函数，用于指定自定义的元素比较操作。这使得equal算法非常灵活，可以适应各种比较需求。
}

bool isEven(int num) {
    return num % 2 == 0;
}
//14.2.1.7 find_if：在容器中查找满足特定条件的元素
void test14_2_1_7() {
    std::cout << "14.2.1.7 find_if：在容器中查找满足特定条件的元素" << std::endl;
    //示例中，我们创建了一个vector容器numbers，其中包含一些整数。然后，我们定义了一个名为isEven的函数，用于判断一个整数是否为偶数。
    //接下来，我们使用std::find_if算法来查找容器中满足isEven条件的第一个元素，并将结果存储在迭代器it中。
    //最后，我们根据迭代器的结果输出相应的信息。
    std::vector<int> numbers = {1, 2, 3, 4, 5};
    auto it = std::find_if(numbers.begin(),numbers.end(),isEven);
    if (it != numbers.end()) {
        std::cout << "First even number found: " << *it << std::endl;
    } else {
        std::cout << "No even number found." << std::endl;
    }
}
