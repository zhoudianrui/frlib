package io.vicp.frlib.algorithm;

/** Description:
 * Given a sorted array consisting of only integers where every element appears twice except for one element which appears once.
 * Find this single element that appears only once.
 *
 * Example1:
 * Input: [1,1,2,3,3,4,4,8,8]
 * Output: 2
 *
 * Example2:
 * Input: [3,3,7,7,10,11,11]
 * Output: 10
 * Created by zhoudr on 2017/3/23.
 */
public class SingleNonDuplicate {

    public static void main(String[] args) {
        int[] nums = {1, 1, 2, 2, 5, 5, 9};
        int result = singleNonDuplicate(nums);
        System.out.println(result);
    }

    /**
     * solution
     * @param nums
     * @return
     */
    public static int singleNonDuplicate(int[] nums) {
        int result = -1;
        if (nums == null || nums.length == 0) {
            return result;
        }
        int low = 0;
        int high = nums.length - 1;
        while(low < high) {
            int middle = (low + high + 1)/2;
            if ((low + 1 == middle) && (middle + 1 == high)) {
                boolean leftResult = nums[middle - 1] == nums[middle];
                if (leftResult) {
                    return nums[high];
                } else {
                    return nums[low];
                }
            }
            int middleSize = high - middle;
            boolean leftResult = nums[middle - 1] == nums[middle];
            boolean rightResult = nums[middle] == nums[middle + 1];
            if (middleSize % 2 == 0) { // 偶数
                if (!leftResult && !rightResult) {
                    result = nums[middle];
                    break;
                } else if (leftResult) {
                    // 向右
                    high = middle;
                } else if (rightResult) {
                    // 向左
                    low = middle;
                }
            } else { // 奇数
                if (!leftResult && !rightResult) {
                    result = nums[middle];
                    break;
                } else if (leftResult) {
                    // 向右
                    low = middle + 1;
                } else if (rightResult) {
                    // 向左
                    high = middle - 1;
                }
            }
        }
        return result;
    }
}
