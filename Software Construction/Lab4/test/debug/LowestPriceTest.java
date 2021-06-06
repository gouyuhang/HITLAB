package debug;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

public class LowestPriceTest {
	// Testing Strategy
	// 测试样例中给的数值
	// 测试一些边界情况
	@Test
	public void test1() {
		List<Integer> price = new ArrayList<>();
		List<List<Integer>> special = new ArrayList<>();
		List<Integer> need = new ArrayList<>();
		price.add(2);
		price.add(5);
		List<Integer> offer1 = new ArrayList<>();
		offer1.add(3);
		offer1.add(0);
		offer1.add(5);
		List<Integer> offer2 = new ArrayList<>();
		offer2.add(1);
		offer2.add(2);
		offer2.add(10);
		special.add(offer1);
		special.add(offer2);
		need.add(3);
		need.add(2);
		LowestPrice l = new LowestPrice();
		assertEquals(14, l.shoppingOffers(price, special, need));

	}

	@Test
	public void test2() {
		List<Integer> price = new ArrayList<>();
		List<List<Integer>> special = new ArrayList<>();
		List<Integer> need = new ArrayList<>();
		price.add(2);
		price.add(3);
		price.add(4);
		List<Integer> offer1 = new ArrayList<>();
		offer1.add(1);
		offer1.add(1);
		offer1.add(0);
		offer1.add(4);
		List<Integer> offer2 = new ArrayList<>();
		offer2.add(2);
		offer2.add(2);
		offer2.add(1);
		offer2.add(9);
		special.add(offer1);
		special.add(offer2);
		need.add(1);
		need.add(2);
		need.add(1);
		LowestPrice l = new LowestPrice();
		assertEquals(11, l.shoppingOffers(price, special, need));

	}

}
