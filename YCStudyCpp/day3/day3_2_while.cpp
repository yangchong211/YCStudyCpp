//
// Created by 杨充 on 2024/7/1.
//
#include <iostream>
#include <string>

using namespace std;


//3.2.1.1 while循环语句
void test3_2_1_1();
//3.2.1.2 do...while循环
void test3_2_1_2();
//3.2.1.3 while与do...while比较
void test3_2_1_3();
//3.2.1.4 for循环语句
void test3_2_1_4();



//3.2.2.2 break语句
void test3_2_2_1();
//3.2.2.3 continue语句
void test3_2_2_3();
//3.2.2.4 goto语句
void test3_2_2_4();
//3.2.2.5 return语句
void test3_2_2_5();



int main() {
    test3_2_1_1();
    test3_2_1_2();
    test3_2_1_3();
    test3_2_1_4();

    test3_2_2_1();
    test3_2_2_3();
    test3_2_2_4();
    test3_2_2_5();
    return 0;
}

//3.2.1.1 while循环语句
void test3_2_1_1() {
    //C++ 中 while 循环的语法：
    //while(condition)
    //{
    //   statement(s);
    //}
    //在这里，statement(s) 可以是一个单独的语句，也可以是几个语句组成的代码块。condition 可以是任意的表达式，当为任意非零值时都为真。当条件为真时执行循环。
    //当条件为假时，程序流将继续执行紧接着循环的下一条语句。

    // 局部变量声明
    int a = 10;
    // while 循环执行
    while (a < 20) {
        cout << "a 的值：" << a << endl;
        a++;
    }
}

//3.2.1.2 do...while循环
void test3_2_1_2() {
    //不像 for 和 while 循环，它们是在循环头部测试循环条件。do...while 循环是在循环的尾部检查它的条件。
    //do...while 循环与 while 循环类似，但是 do...while 循环会确保至少执行一次循环。
    //C++ 中 do...while 循环的语法：
    //do
    //{
    //   statement(s);
    //
    //}while( condition );
    //请注意，条件表达式出现在循环的尾部，所以循环中的 statement(s) 会在条件被测试之前至少执行一次。
    //如果条件为真，控制流会跳转回上面的 do，然后重新执行循环中的 statement(s)。这个过程会不断重复，直到给定条件变为假为止。

    // 局部变量声明
    int a = 10;
    // do 循环执行
    do {
        cout << "a 的值：" << a << endl;
        a = a + 1;
    } while (a < 20);
}


//3.2.1.3 while与do...while比较
void test3_2_1_3() {

}


//3.2.1.4 for循环语句
void test3_2_1_4() {
    //for 循环允许您编写一个执行特定次数的循环的重复控制结构。
    //C++ 中 for 循环的语法：
    //for ( init; condition; increment )
    //{
    //   statement(s);
    //}


    //下面是 for 循环的控制流：
    //init 会首先被执行，且只会执行一次。这一步允许您声明并初始化任何循环控制变量。您也可以不在这里写任何语句，只要有一个分号出现即可。
    //接下来，会判断 condition。如果为真，则执行循环主体。如果为假，则不执行循环主体，且控制流会跳转到紧接着 for 循环的下一条语句。
    //在执行完 for 循环主体后，控制流会跳回上面的 increment 语句。该语句允许您更新循环控制变量。该语句可以留空，只要在条件后有一个分号出现即可。
    //条件再次被判断。如果为真，则执行循环，这个过程会不断重复（循环主体，然后增加步值，再然后重新判断条件）。在条件变为假时，for 循环终止。
    // for 循环执行
    for (int a = 10; a < 20; a = a + 1) {
        cout << "a 的值：" << a << endl;
    }
}


//3.2.2.2 break语句
void test3_2_2_1() {
    //C++ 中 break 语句有以下两种用法：
    //当 break 语句出现在一个循环内时，循环会立即终止，且程序流将继续执行紧接着循环的下一条语句。
    //它可用于终止 switch 语句中的一个 case。
    //如果您使用的是嵌套循环（即一个循环内嵌套另一个循环），break 语句会停止执行最内层的循环，然后开始执行该块之后的下一行代码。
    // 局部变量声明
    int a = 10;
    // do 循环执行
    do {
        cout << "a 的值：" << a << endl;
        a = a + 1;
        if (a > 15) {
            // 终止循环
            break;
        }
    } while (a < 20);
}


//3.2.2.3 continue语句
void test3_2_2_3() {
    //C++ 中的 continue 语句有点像 break 语句。但它不是强迫终止，continue 会跳过当前循环中的代码，强迫开始下一次循环。
    //对于 for 循环，continue 语句会导致执行条件测试和循环增量部分。
    //对于 while 和 do...while 循环，continue 语句会导致程序控制回到条件测试上。
    // 局部变量声明
    int a = 10;
    // do 循环执行
    do {
        if (a == 15) {
            // 跳过迭代
            a = a + 1;
            continue;
        }
        cout << "a 的值：" << a << endl;
        a = a + 1;
    } while (a < 20);
}


//3.2.2.4 goto语句
void test3_2_2_4() {
    //goto 语句允许把控制无条件转移到同一函数内的被标记的语句。
    //
    //注意：在任何编程语言中，都不建议使用 goto 语句。因为它使得程序的控制流难以跟踪，使程序难以理解和难以修改。
    //任何使用 goto 语句的程序可以改写成不需要使用 goto 语句的写法。

}


//3.2.2.5 return语句
void test3_2_2_5() {
    cout << "3.2.2.5 return语句" << endl;
    //return语句：return语句用于立即终止当前函数的执行，并返回到调用该函数的地方。它可以用于在任何地方结束循环。例如：
    for (int i = 0; i < 10; i++) {
        if (i == 5) {
            cout << "执行循环体的代码块 return语句 "<< endl;
            return; // 当 i 等于 5 时，结束函数的执行
        }
        // 执行循环体的代码块
        cout << "执行循环体的代码块 " << i << endl;
    }
    // 这里的代码不会执行，因为函数已经返回
    cout << "这里的代码不会执行" << endl;
}



