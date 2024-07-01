//
// Created by 杨充 on 2024/7/1.
//
#include <iostream>
#include <string>

using namespace std;


//3.1.1.1 if语句
void test3_1_1_1();
//3.1.1.2 if...else语句
void test3_1_1_2();
//3.1.1.3 嵌套的if-else语句
void test3_1_1_3();
//3.1.1.4 嵌套 if 语句
void test3_1_1_4();
//3.1.1.5 使用条件运算符进行判断
void test3_1_1_5();
//3.1.1.6 switch判断语句
void test3_1_1_6();


int main() {
    test3_1_1_1();
    test3_1_1_2();
    test3_1_1_3();
    test3_1_1_4();
    test3_1_1_5();
    test3_1_1_6();
    return 0;
}

//3.1.1.1 if语句
void test3_1_1_1() {
    //一个 if 语句 由一个布尔表达式后跟一个或多个语句组成。
    //如果布尔表达式为 true，则 if 语句内的代码块将被执行。如果布尔表达式为 false，则 if 语句结束后的第一组代码（闭括号后）将被执行。
    //C 语言把任何非零和非空的值假定为 true，把零或 null 假定为 false。
    // 局部变量声明
    int a = 10;
    // 使用 if 语句检查布尔条件
    if (a < 20) {
        // 如果条件为真，则输出下面的语句
        cout << "a 小于 20" << endl;
    }
    cout << "a 的值是 " << a << endl;
}


//3.1.1.2 if...else语句
void test3_1_1_2() {
    //一个 if 语句 后可跟一个可选的 else 语句，else 语句在布尔表达式为假时执行。
    // 局部变量声明
    int a = 100;

    // 检查布尔条件
    if (a < 20) {
        // 如果条件为真，则输出下面的语句
        cout << "a 小于 20" << endl;
    } else {
        // 如果条件为假，则输出下面的语句
        cout << "a 大于 20" << endl;
    }
    cout << "a 的值是 " << a << endl;
}


//3.1.1.3 嵌套的if-else语句
void test3_1_1_3() {
    //if...else if...else 语句
    //一个 if 语句后可跟一个可选的 else if...else 语句，这可用于测试多种条件。
    //当使用 if...else if...else 语句时，以下几点需要注意：
    //一个 if 后可跟零个或一个 else，else 必须在所有 else if 之后。
    //一个 if 后可跟零个或多个 else if，else if 必须在 else 之前。
    //一旦某个 else if 匹配成功，其他的 else if 或 else 将不会被测试。

    // 局部变量声明
    int a = 100;

    // 检查布尔条件
    if (a == 10) {
        // 如果 if 条件为真，则输出下面的语句
        cout << "a 的值是 10" << endl;
    } else if (a == 20) {
        // 如果 else if 条件为真，则输出下面的语句
        cout << "a 的值是 20" << endl;
    } else if (a == 30) {
        // 如果 else if 条件为真，则输出下面的语句
        cout << "a 的值是 30" << endl;
    } else {
        // 如果上面条件都不为真，则输出下面的语句
        cout << "没有匹配的值" << endl;
    }
    cout << "a 的准确值是 " << a << endl;
}

//3.1.1.4 嵌套 if 语句
void test3_1_1_4() {
    //在 C++ 中，嵌套 if-else 语句是合法的，这意味着您可以在一个 if 或 else if 语句内使用另一个 if 或 else if 语句。
    //嵌套 if 语句是一种 if 语句的变体，其中一个 if 语句可以在另一个 if 语句中嵌套。
    //嵌套 if 语句可以帮助您更精确地测试多个条件。
    int x = 15;
    if (x < 20) {
        cout << "x 小于 20" << endl;
        if (x < 15) {
            cout << "x 小于 15" << endl;
        } else {
            cout << "x 大于等于 15" << endl;
        }
    } else {
        cout << "x 大于等于 20" << endl;
    }
}

//3.1.1.5 使用条件运算符进行判断
void test3_1_1_5() {
    cout << "3.1.1.5 使用条件运算符进行判断" << endl;
    //它的语法形式如下：
    //condition ? expression1 : expression2
    //其中，condition 是一个条件表达式，如果条件为真，则返回 expression1 的值，否则返回 expression2 的值。
    //以下是一个简单的示例，演示如何使用条件运算符进行判断：
    int num = 10;
    std::string result = (num > 5) ? "Greater than 5" : "Less than or equal to 5";
    std::cout << result << std::endl;
}


//3.1.1.6 switch判断语句
void test3_1_1_6() {
    //它的语法形式如下：
    //switch (expression) {
    //    case value1:
    //        // 执行语句块1
    //        break;
    //    case value2:
    //        // 执行语句块2
    //        break;
    //    // 可以有更多的 case 分支
    //    default:
    //        // 执行默认语句块
    //        break;
    //}
    //switch语句中的 expression 是一个表达式，它的值将与每个 case 分支的值进行比较。
    //如果 expression 的值与某个 case 分支的值相等，则执行该分支下的语句块。如果没有匹配的 case 分支，可以使用 default 分支来执行默认的语句块。
    //在每个 case 分支的语句块中，可以包含一系列的语句。在执行完相应的语句块后，可以使用 break 语句来跳出 switch 语句，防止继续执行后续的分支。
    //如果没有使用 break 语句，将会继续执行下一个分支的语句块，直到遇到 break 或 switch 语句结束。
    int num = 2;
    switch (num) {
        case 1:
            std::cout << "Number is 1" << std::endl;
            break;
        case 2:
            std::cout << "Number is 2" << std::endl;
            break;
        case 3:
            std::cout << "Number is 3" << std::endl;
            break;
        default:
            std::cout << "Number is not 1, 2, or 3" << std::endl;
            break;
    }
}




