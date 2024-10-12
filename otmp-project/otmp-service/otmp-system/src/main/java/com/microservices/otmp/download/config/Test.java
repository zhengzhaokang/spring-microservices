package com.microservices.otmp.download.config;

import com.alibaba.fastjson.JSON;
import com.microservices.otmp.download.service.ReportExportTaskService;
import jodd.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import scala.util.Random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
public class Test {

    @Autowired
    private static ReportExportTaskService reportExportTaskService;

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        int[] test = new int[]{1,2,3,4};
        String str = "abcd";
        System.out.println(str.substring(0,2));

//        Future<String> future = reportExportTaskService.threadTest();
//        String result = future.get(1, TimeUnit.SECONDS);
//        System.out.println(result);
    }

    /**
     * 给你一个下标从 1 开始的整数数组 numbers ，该数组已按 非递减顺序排列  ，请你从数组中找出满足相加之和等于目标数 target 的两个数。
     * 如果设这两个数分别是 numbers[index1] 和 numbers[index2] ，则 1 <= index1 < index2 <= numbers.length 。
     *
     * 以长度为 2 的整数数组 [index1, index2] 的形式返回这两个整数的下标 index1 和 index2。
     *
     * 你可以假设每个输入 只对应唯一的答案 ，而且你 不可以 重复使用相同的元素。
     *
     * 你所设计的解决方案必须只使用常量级的额外空间。
     *
     *
     * 示例 1：
     *
     * 输入：numbers = [2,7,11,15], target = 9
     * 输出：[1,2]
     * 解释：2 与 7 之和等于目标数 9 。因此 index1 = 1, index2 = 2 。返回 [1, 2] 。
     */
    public int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum == target) {
                return new int[]{left + 1, right + 1};
            } else if (sum > target) {
                right--;
            } else {
                left++;
            }
        }
        return null;
    }

    /**
     * 给定字符串 s 和 t ，判断 s 是否为 t 的子序列。
     *
     * 字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。
     * （例如，"ace"是"abcde"的一个子序列，而"aec"不是）。
     * 进阶：
     *
     * 如果有大量输入的 S，称作 S1, S2, ... , Sk 其中 k >= 10亿，你需要依次检查它们是否为 T 的子序列。
     * 在这种情况下，你会怎样改变代码？
     *
     * 示例 1：
     * 输入：s = "abc", t = "ahbgdc"
     * 输出：true
     * 示例 2：
     * 输入：s = "axc", t = "ahbgdc"
     * 输出：false
     */
    public boolean isSubsequence(String s, String t) {
        int m = s.length();
        int n = t.length();
        int i = 0;
        int j = 0;
        while (i < m && j < n) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }
            j++;
        }
        return i == m;
    }

    /**
     * 如果在将所有大写字符转换为小写字符、并移除所有非字母数字字符之后，短语正着读和反着读都一样。则可以认为该短语是一个 回文串 。
     *
     * 字母和数字都属于字母数字字符。
     *
     * 给你一个字符串 s，如果它是 回文串 ，返回 true ；否则，返回 false 。
     *
     * 示例 1：
     *
     * 输入: s = "A man, a plan, a canal: Panama"
     * 输出：true
     * 解释："amanaplanacanalpanama" 是回文串
     */
    public boolean isPalindrome(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                stringBuilder.append(Character.toLowerCase(c));
            }
        }
        String str = stringBuilder.toString();
//        String str = s.toLowerCase().replaceAll("[^a-z0-9]", "");
        return str.equals(new StringBuilder(str).reverse().toString());
    }

    /**
     * 给你两个字符串 haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串的第一个匹配项的下标（下标从 0 开始）。如果 needle 不是 haystack 的一部分，则返回  -1 。
     *
     * 示例 1：
     *
     * 输入：haystack = "sadbutsad", needle = "sad"
     * 输出：0
     * 解释："sad" 在下标 0 和 6 处匹配。
     * 第一个匹配项的下标是 0 ，所以返回 0 。
     */
    public int strStr(String haystack, String needle) {
        int m = haystack.length();
        int n = needle.length();
        char[] p = haystack.toCharArray();
        char[] q = needle.toCharArray();
        for (int i = 0; i + n <= m; i++) {
            int a = i, b = 0;
            while(b < n && p[a] == q[b]) {
                a++;
                b++;
            }
            if (b == n) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 将一个给定字符串 s 根据给定的行数 numRows ，以从上往下、从左到右进行 Z 字形排列。
     *
     * 比如输入字符串为 "PAYPALISHIRING" 行数为 3 时，排列如下：
     *
     * P   A   H   N
     * A P L S I I G
     * Y   I   R
     * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："PAHNAPLSIIGYIR"。
     *
     * 请你实现这个将字符串进行指定行数变换的函数：
     *
     * string convert(string s, int numRows);
     *
     *
     * 示例 1：  0 3+1 8  12  (numRows+1)*
     *
     * 输入：s = "PAYPALISHIRING", numRows = 3
     * 输出："PAHNAPLSIIGYIR"
     */
    public String convert(String s, int numRows) {
        if (numRows < 2) {
            return s;
        }
        List<StringBuilder> rows = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            rows.add(new StringBuilder());
        }
        int index = 0;
        int flag = -1; // 转向用
        for (char c : s.toCharArray()) {
            rows.get(index).append(c);
            if (index == 0 || index == numRows - 1) {
                flag = -flag;
            }
            index += flag;
        }
        StringBuilder res = new StringBuilder();
        for (StringBuilder row : rows) {
            res.append(row);
        }
        return res.toString();
    }

    /**
     * 给你一个字符串 s ，请你反转字符串中 单词 的顺序。
     *
     * 单词 是由非空格字符组成的字符串。s 中使用至少一个空格将字符串中的 单词 分隔开。
     *
     * 返回 单词 顺序颠倒且 单词 之间用单个空格连接的结果字符串。
     *
     * 注意：输入字符串 s中可能会存在前导空格、尾随空格或者单词间的多个空格。返回的结果字符串中，单词间应当仅用单个空格分隔，且不包含任何额外的空格。
     *
     *
     * 示例 1：
     *
     * 输入：s = "the sky is blue"
     * 输出："blue is sky the"
     */
    public String reverseWords(String s) {
        String[] split = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = split.length - 1; i >= 0; i--) {
            if (split[i].isEmpty()) {
                continue;
            }
            sb.append(split[i] + " ");
        }
        return sb.toString().trim();
    }

    /**
     * 编写一个函数来查找字符串数组中的最长公共前缀。
     *
     * 如果不存在公共前缀，返回空字符串 ""。
     *
     * 示例 1：
     * 输入：strs = ["flower","flow","flight"]
     * 输出："fl"
     */
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        String prefix = strs[0];
        int count = strs.length;
        for (int i = 1; i < count; i++) {
            prefix = longestCommonPrefix1(prefix, strs[i]);
            if (prefix.isEmpty()) {
                break;
            }
        }
        return prefix;
    }

    private static String longestCommonPrefix1(String prefix, String str) {
        int length = Math.min(prefix.length(), str.length());
        int index = 0;
        while(index < length && prefix.charAt(index) == str.charAt(index)) {
            index++;
        }
        return prefix.substring(0, index);
    }

    /**
     * 给你一个字符串 s，由若干单词组成，单词前后用一些空格字符隔开。返回字符串中 最后一个 单词的长度。
     *
     * 单词 是指仅由字母组成、不包含任何空格字符的最大子字符串。
     *
     *
     *
     * 示例 1：
     *
     * 输入：s = "Hello World"
     * 输出：5
     * 解释：最后一个单词是“World”，长度为5。
     */
    public int lengthOfLastWord(String s) {
        String[] split = s.split(" ");
        return split[split.length -1].length();
    }

    /**
     * 整数转罗马数字
     *根据罗马数字的唯一表示法，为了表示一个给定的整数 num\textit{num}num，我们寻找不超过 num\textit{num}num 的最大符号值，
     * 将 num\textit{num}num 减去该符号值，然后继续寻找不超过 num\textit{num}num 的最大符号值，
     * 将该符号拼接在上一个找到的符号之后，循环直至 num\textit{num}num 为 000。
     * 最后得到的字符串即为 num\textit{num}num 的罗马数字表示。
     *
     * 编程时，可以建立一个数值-符号对的列表 valueSymbols\textit{valueSymbols}valueSymbols，
     * 按数值从大到小排列。遍历 valueSymbols\textit{valueSymbols}valueSymbols 中的每个数值-符号对，
     * 若当前数值 value\textit{value}value 不超过 num\textit{num}num，
     * 则从 num\textit{num}num 中不断减去 value\textit{value}value，直至 num\textit{num}num 小于
     * value\textit{value}value，然后遍历下一个数值-符号对。若遍历中 num\textit{num}num 为 000 则跳出循环。
     *
     * @param num
     * @return
     */
    public String intToRoman(int num) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                num -= values[i];
                buffer.append(symbols[i]);
            }
            if (num == 0) {
                break;
            }
        }
        return buffer.toString();
    }

    /**
     * 罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。
     *
     * 字符          数值
     * I             1
     * V             5
     * X             10
     * L             50
     * C             100
     * D             500
     * M             1000
     * 例如， 罗马数字 2 写做 II ，即为两个并列的 1 。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
     *
     * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。
     * 数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
     *
     * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
     * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
     * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
     * 给定一个罗马数字，将其转换成整数。
     *
     * 示例 2:
     *
     * 输入: s = "IV"
     * 输出: 4
     * 示例 3:
     *
     * 输入: s = "IX"
     * 输出: 9
     * 示例 4:
     *
     * 输入: s = "LVIII"
     * 输出: 58
     * 解释: L = 50, V= 5, III = 3.
     * 示例 5:
     *
     * 输入: s = "MCMXCIV"
     * 输出: 1994
     * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
     *
     * 罗马数字由 I,V,X,L,C,D,M 构成；
     * 当小值在大值的左边，则减小值，如 IV=5-1=4；
     * 当小值在大值的右边，则加小值，如 VI=5+1=6；
     * 由上可知，右值永远为正，因此最后一位必然为正。
     * 一言蔽之，把一个小值放在大值的左边，就是做减法，否则为加法。
     *
     * 在代码实现上，可以往后看多一位，对比当前位与后一位的大小关系，从而确定当前位是加还是减法。当没有下一位时，做加法即可。
     *
     * 也可保留当前位的值，当遍历到下一位的时，对比保留值与遍历位的大小关系，再确定保留值为加还是减。最后一位做加法即可。
     *
     */
    public static int romanToInt(String s) {
        int sum = 0;
        int pre = getValue(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            int cur = getValue(s.charAt(i));
            if (pre < cur) {
                sum -= pre;
            } else {
                sum += pre;
            }
            pre = cur;
        }
        sum += pre;
        return sum;
    }

    public  static void testThreadPoolExecutor() {
        // 创建线程池参数：核心线程数、最大线程数、空闲线程存活时间、时间单位、任务队列、拒绝策略、线程工厂
        int corePoolSize = 5; // 核心线程数
        int maximumPoolSize = 10; // 最大线程数
        long keepAliveTime = 60L; // 空闲线程存活时间
        TimeUnit unit = TimeUnit.SECONDS; // 时间单位
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10); // 任务队列
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy(); // 拒绝策略
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("my-thread-pool-%d").get(); // 线程工厂

        // 创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                handler);

    }

    @Async("bankTransferTaskExecutor")
    public Future<String> threadTest() {
        log.info("{} start...", Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("{} end...", Thread.currentThread().getName());
        return new AsyncResult<>(Thread.currentThread().getName());
    }

    private static int getValue(char c) {
        switch (c) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }
    }

    /**
     * 在一条环路上有 n 个加油站，其中第 i 个加油站有汽油 gas[i] 升。
     *
     * 你有一辆油箱容量无限的的汽车，从第 i 个加油站开往第 i+1 个加油站需要消耗汽油 cost[i] 升。你从其中的一个加油站出发，开始时油箱为空。
     *
     * 给定两个整数数组 gas 和 cost ，如果你可以按顺序绕环路行驶一周，则返回出发时加油站的编号，否则返回 -1 。如果存在解，则 保证 它是 唯一 的。
     *
     * 输入: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
     * 输出: 3
     * 解释:
     * 从 3 号加油站(索引为 3 处)出发，可获得 4 升汽油。此时油箱有 = 0 + 4 = 4 升汽油
     * 开往 4 号加油站，此时油箱有 4 - 1 + 5 = 8 升汽油
     * 开往 0 号加油站，此时油箱有 8 - 2 + 1 = 7 升汽油
     * 开往 1 号加油站，此时油箱有 7 - 3 + 2 = 6 升汽油
     * 开往 2 号加油站，此时油箱有 6 - 4 + 3 = 5 升汽油
     * 开往 3 号加油站，你需要消耗 5 升汽油，正好足够你返回到 3 号加油站。
     * 因此，3 可为起始索引。
     *
     * 输入: gas = [2,3,4], cost = [3,4,3]
     * 输出: -1
     * 解释:
     * 你不能从 0 号或 1 号加油站出发，因为没有足够的汽油可以让你行驶到下一个加油站。
     * 我们从 2 号加油站出发，可以获得 4 升汽油。 此时油箱有 = 0 + 4 = 4 升汽油
     * 开往 0 号加油站，此时油箱有 4 - 3 + 2 = 3 升汽油
     * 开往 1 号加油站，此时油箱有 3 - 3 + 3 = 3 升汽油
     * 你无法返回 2 号加油站，因为返程需要消耗 4 升汽油，但是你的油箱只有 3 升汽油。
     * 因此，无论怎样，你都不可能绕环路行驶一周。
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;
        int i = 0;
        while (i < n) {
            int gasSum = 0;
            int costSum = 0;
            int cnt = 0;
            while (cnt < n) {
                int j = (i + cnt) % n; //当前点对应的油站节点  i+cnt 摸 n
                gasSum += gas[j];
                costSum += cost[j];
                if (costSum > gasSum) {
                    break;
                }
                cnt++;
            }
            if (cnt == n) {
                return i;
            } else {
                i = i + cnt + 1; // 从无法到达的地方，下一站开始检查，无法达到，说明从开始到此，任意一个点都无法
            }
        }
        return -1;
    }

    public int canCompleteCircuit1(int[] gas, int[] cost) {
        int n = gas.length;
        int i = 0;
        while (i < n) {
            int sumOfGas = 0, sumOfCost = 0;
            int cnt = 0;
            while (cnt < n) {
                int j = (i + cnt) % n;
                sumOfGas += gas[j];
                sumOfCost += cost[j];
                if (sumOfCost > sumOfGas) {
                    break;
                }
                cnt++;
            }
            if (cnt == n) {
                return i;
            } else { // 从无法到达的地方，下一站开始检查，无法达到，说明从开始到此，任意一个点都无法到达
                i = i + cnt + 1;
            }
        }
        return -1;
    }

    /**
     * 给你一个整数数组 nums，返回 数组 answer ，其中 answer[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积 。
     *
     * 题目数据 保证 数组 nums之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内。
     *
     * 请 不要使用除法，且在 O(n) 时间复杂度内完成此题。
     *
     *示例 1:
     *
     * 输入: nums = [1,2,3,4]
     * 输出: [24,12,8,6]
     * 示例 2:
     *
     * 输入: nums = [-1,1,0,-3,3]
     * 输出: [0,0,9,0,0]
     *
     * 2 <= nums.length <= 105
     * -30 <= nums[i] <= 30
     */
    public static int[] productExceptSelf(int[] nums) {
        // 定义左乘积数组、右乘积数组、结果数组
        int length = nums.length;
        int[] left = new int[length];
        int[] right = new int[length];
        int[] answer = new int[length];

        left[0] = 1;
        right[length - 1] = 1;
        for (int i = 1; i < length; i++) {
            left[i] = left[i - 1] * nums[i - 1];
        }
        for (int i = length - 2; i >= 0; i--) {
            right[i] = right[i + 1] * nums[i + 1];
        }
        for (int i = 0; i < length; i++) {
            answer[i] = left[i] * right[i];
        }

        return answer;
    }

    /**
     * 时间插入、删除和获取随机元素
     * 实现RandomizedSet 类：
     *
     * RandomizedSet() 初始化 RandomizedSet 对象
     * bool insert(int val) 当元素 val 不存在时，向集合中插入该项，并返回 true ；否则，返回 false 。
     * bool remove(int val) 当元素 val 存在时，从集合中移除该项，并返回 true ；否则，返回 false 。
     * int getRandom() 随机返回现有集合中的一项（测试用例保证调用此方法时集合中至少存在一个元素）。每个元素应该有 相同的概率 被返回。
     * 你必须实现类的所有函数，并满足每个函数的 平均 时间复杂度为 O(1) 。
     *
     * 输入
     * ["RandomizedSet", "insert", "remove", "insert", "getRandom", "remove", "insert", "getRandom"]
     * [[], [1], [2], [2], [], [1], [2], []]
     * 输出
     * [null, true, false, true, 2, true, false, 2]
     */
    class RandomizedSet {

        List<Integer> list;

        Map<Integer, Integer> map;

        Random random;

        public RandomizedSet() {
            list = new ArrayList<>();
            map = new HashMap<>();
            random = new Random();
        }

        public boolean insert(int val) {
            if (map.containsKey(val)) {
                return false;
            }
            map.put(val, list.size());
            list.add(val);
            return true;
        }

        public boolean remove(int val) {
            if (!map.containsKey(val)) {
                return false;
            }
            int index = map.get(val);
            int last = list.get(list.size() - 1);
            list.set(index, last);
            map.put(last, index);
            list.remove(list.size() - 1);
            map.remove(val);
            return true;
        }

        public int getRandom() {
            int randomIndex = random.nextInt(list.size());
            return list.get(randomIndex);
        }
    }

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */

    /**
     * 给你一个整数数组 citations ，其中 citations[i] 表示研究者的第 i 篇论文被引用的次数。计算并返回该研究者的 h 指数。
     *
     * 根据维基百科上 h 指数的定义：h 代表“高引用次数” ，一名科研人员的 h 指数 是指他（她）至少发表了 h 篇论文，
     * 并且 至少 有 h 篇论文被引用次数大于等于 h 。如果 h 有多种可能的值，h 指数 是其中最大的那个。
     *
     * 示例 1：
     *
     * 输入：citations = [3,0,6,1,5]
     * 输出：3
     * 解释：给定数组表示研究者总共有 5 篇论文，每篇论文相应的被引用了 3, 0, 6, 1, 5 次。
     *      由于研究者有 3 篇论文每篇 至少 被引用了 3 次，其余两篇论文每篇被引用 不多于 3 次，所以她的 h 指数是 3。
     * 示例 2：
     *
     * 输入：citations = [1,3,1]
     * 输出：1
     */
    public int hIndex(int[] citations) {
        Arrays.sort(citations);
        int n = citations.length;
        for (int i = 0; i < n; ++i) {
            // n - i -> n 的引用次数均 >= citations[i]
            if (citations[i] >= n - i) {
                return n - i;
            }
        }
        return 0;
    }

    /**
     * 给定一个长度为 n 的 0 索引整数数组 nums。初始位置为 nums[0]。
     *
     * 每个元素 nums[i] 表示从索引 i 向前跳转的最大长度。换句话说，如果你在 nums[i] 处，你可以跳转到任意 nums[i + j] 处:
     *
     * 0 <= j <= nums[i]
     * i + j < n
     * 返回到达 nums[n - 1] 的最小跳跃次数。生成的测试用例可以到达 nums[n - 1]。
     * 示例 1:
     *
     * 输入: nums = [2,3,1,1,4]
     * 输出: 2
     * 解释: 跳到最后一个位置的最小跳跃数是 2。
     *      从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
     * 示例 2:
     *
     * 输入: nums = [2,3,0,1,4]
     * 输出: 2
     */
    public static int jump(int[] nums) {
        int maxJump = 0;
        int end = 0;
        int step = 0;
        // 不访问最后一个元素
        for (int i = 0; i < nums.length - 1; i++) {
            if (i <= maxJump) {
                maxJump = Math.max(maxJump, i + nums[i]);
                // 维护每次最大可跳跃的位置的选择 [2,3,1,2,1,2,3]
                // 从左至右遍历数组，到达边界时，更新边界，并将跳跃次数+1
                if (i == end) {
                    end = maxJump;
                    step++;
                }
            }
        }
        return step;
    }

    /**
     * 给你一个非负整数数组 nums ，你最初位于数组的 第一个下标 。数组中的每个元素代表你在该位置可以跳跃的最大长度。
     *
     * 判断你是否能够到达最后一个下标，如果可以，返回 true ；否则，返回 false 。
     *
     * 输入：nums = [2,3,1,1,4]  i + nums[i] >= nums.length -1
     * 输出：true
     * 解释：可以先跳 1 步，从下标 0 到达下标 1, 然后再从下标 1 跳 3 步到达最后一个下标。
     *
     * 输入：nums = [3,2,1,0,4]
     * 输出：false
     * 解释：无论怎样，总会到达下标为 3 的位置。但该下标的最大跳跃长度是 0 ， 所以永远不可能到达最后一个下标。
     *
     * i 最远可跳的距离 maxJump = i + nums[i] 只有当 i < maxJump 时才更新 max
     */
    public static boolean canJump(int[] nums) {
        int maxJump = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i <= maxJump) {
                maxJump = Math.max(maxJump, i + nums[i]);
                if (maxJump >= nums.length - 1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 给你一个整数数组 prices ，其中 prices[i] 表示某支股票第 i 天的价格。
     *
     * 在每一天，你可以决定是否购买和/或出售股票。你在任何时候 最多 只能持有 一股 股票。你也可以先购买，然后在 同一天 出售。
     *
     * 返回 你能获得的 最大 利润 。
     *
     * 输入：prices = [7,1,5,3,6,4]
     * 输出：7
     * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5 - 1 = 4 。
     *      随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6 - 3 = 3 。
     *      总利润为 4 + 3 = 7 。
     *
     * @param prices
     * @return
     */
    public int maxProfitPro(int[] prices) {
        if (prices.length <= 1) {
            return 0;
        }
        // 贪心算法
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            profit += Math.max(prices[i] - prices[i - 1], 0);
        }

        return profit;
    }

    public static int maxProfit1(int[] prices) {
        // 1.股票最低点买入，最高点卖出 贪心算法
        int maxProfit = 0;
        int minPrice = Integer.MAX_VALUE;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            } else if (prices[i] - minPrice > maxProfit) {
                maxProfit = prices[i] - minPrice;
            }
        }
        return maxProfit;
    }

    /**
     *给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
     *
     * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
     *
     * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
     *
     *输入：[7,1,5,3,6,4]
     * 输出：5
     * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
     *      注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
     */
    public static int maxProfit(int[] prices) { //超时
        int mount = 0;
        int buy = prices[0];
        for (int i = 0; i < prices.length; i++) {
            for (int j = i; j < prices.length; j++) {
                mount = Math.max(mount, prices[j] - buy);
            }
            buy = prices[i];
        }
        return mount;
    }

    /**
     * 给定一个整数数组 nums，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
     * 输入: nums = [1,2,3,4,5,6,7], k = 3
     * 输出: [5,6,7,1,2,3,4]
     * 解释:
     * 向右轮转 1 步: [7,1,2,3,4,5,6]  4 5 6 7 1 2 3  (i + n -k)%n nums[4] = nums[0]
     * 向右轮转 2 步: [6,7,1,2,3,4,5]  nums[5] = nums[1] nums[6] = nums[2] num[0] = nums[3]
     * 向右轮转 3 步: [5,6,7,1,2,3,4]  4 5 6 7 1 2 3  (n-k)%n 4%7 nums1[4] = nums[0]
     */
    public void rotate(int[] nums, int k) {
        // 起点 下标length - k 下标length -1  0 到 length - k -1
        // (i+k)%length 为目标元素所在的位置
        int[] newNums = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            newNums[(i + k) % nums.length] = nums[i];
        }
        System.arraycopy(newNums, 0, nums, 0, nums.length);
    }

    /**
     * 给定一个大小为 n 的数组 nums ，返回其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
     *
     * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
     */
    public int majorityElement(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }

    /**给你两个按 非递减顺序 排列的整数数组 nums1 和 nums2，另有两个整数 m 和 n ，
     * 分别表示 nums1 和 nums2 中的元素数目。
     请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。 **/
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        //直接合并后排序
        for (int i = 0; i < n; i++) {
            nums1[m + i] = nums2[i];
        }

        Arrays.sort(nums1);
    }

    public void merge1(int[] nums1, int m, int[] nums2, int n) {
        //双指针，p1,p2，分别指向数组nums1,数组nums2
        int p1 = 0, p2= 0;
        int[] sortNum = new int[m + n];
        int cur = 0;
        while (p1 < m || p2 < n) {
            if (p1 == m) {
                //说明只剩下p2了
                cur = nums2[p2++];
            } else if (p2 == n) {
                //说明只剩下p1了
                cur = nums1[p1++];
            } else if (nums1[p1] < nums2[p2]) {
                cur = nums1[p1++];
            } else {
                cur = nums2[p2++];
            }
            //因为每次while，p1、p2只增加一个
            sortNum[p1 + p2 -1] = cur;
        }
        for (int i = 0; i < m + n; i++) {
            nums1[i] = sortNum[i];
        }
    }

    /**
     * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
     * @param nums
     * @param val
     * @return
     */
    public int removeElement(int[] nums, int val) {
        if (nums == null || nums.length < 1) {
            return 0;
        }
        int length = nums.length;
        Arrays.sort(nums);
        int count = 0;
        for (int i = 0; i < length; i++) {
            if (nums[i] == val) {
                nums[i] = nums[length -count -1];
                count++;
            }
        }
        return length - count;
    }

    public int removeElement1(int[] nums, int val) {
        if (nums == null || nums.length < 1) {
            return 0;
        }
        int length = nums.length;
        int left = 0;
        for (int i = 0; i < length; i++) {
            if (nums[i] != val) {
                nums[left] = nums[i];
                left++;
            }
        }
        return left;
    }

    public int removeElement2(int[] nums, int val) {
        if (nums == null || nums.length < 1) {
            return 0;
        }
        int left = 0;
        int right = nums.length;
        while (left < right) {
            if (nums[left] == val) {
                nums[left] = nums[right -1];
                right--;
            } else {
                left++;
            }
        }
        return left;
    }

    /**
     * 删除有序数组中的重复项 输入：nums = [0,0,1,1,1,2,2,3,3,4]
     * 后返回 nums 中唯一元素的个数
     * 输出：5, nums = [0,1,2,3,4]
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {
        int length = nums.length;
        if (length < 1) {
            return 0;
        }
        int left = 0;
        int right = 1;
        while (left < length && right < length) {
            if (nums[left] == nums[right]) {
                right++;
            } else {
                left++;
                nums[left] = nums[right];
            }
        }
        return left + 1;
    }


    /**
     * 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，
     * 使得出现次数超过两次的元素只出现两次 ，返回删除后数组的新长度。
     *
     * 输入：nums = [0,0,1,1,1,1,2,3,3]  0 0 0 0 1 1 1 2 3 3
     * 输出：7, nums = [0,0,1,1,2,3,3]
     *
     * @param nums
     * @return
     */
    public int removeDuplicates2(int[] nums) {
        int length = nums.length;
        if (length <= 2) {
            return length;
        }
        int slow = 2, fast = 2;
        while (fast < length) {
            if (nums[slow - 2] != nums[fast]) {
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        return slow;
    }


}
