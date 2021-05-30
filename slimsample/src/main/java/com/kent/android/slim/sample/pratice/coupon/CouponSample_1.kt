package com.kent.android.slim.sample.pratice.coupon

/**
 * Created by songzhukai on 2021/1/7.
 */
sealed class Coupon {
    companion object {
        val CashType = "CASH"
        val DiscountType = "DISCOUNT"
        val GiftType = "GIFT"

        val NotFetched = 1 //未领取
        val Featched = 2 //已领取
        val Used = 3
        val Expired = 4
        val UnAvilable = 5
        val NotFeatched = 6
    }

    class CashCoupon(val id: Long, val type: String, val leastCost: Long, val reduceCost: Long) : Coupon()
    class DiscountCoupon(val id: Long, val type: String, val discount: Int) : Coupon()
    class GiftCoupon(val id: Long, val type: String, val gift: String) : Coupon()

}


class User

fun fetched(c: Coupon, user: User): Boolean {
    return false
}

fun used(c: Coupon, user: User): Boolean {
    return true
}

fun isExpired(c: Coupon): Boolean {
    return false
}

fun isUnAvilable(c: Coupon): Boolean {
    return false
}

fun getCouponStatus(coupon: Coupon, user: User): Int = when {
    isUnAvilable(coupon) -> Coupon.UnAvilable
    isExpired(coupon) -> Coupon.Expired
    used(coupon, user) -> Coupon.Used
    fetched(coupon, user) -> Coupon.Featched
    else -> Coupon.NotFeatched
}

fun showStatus(coupon: Coupon, user: User) = when (getCouponStatus(coupon, user)) {
    Coupon.UnAvilable -> showUnAvilable()
    Coupon.Expired -> showExpired()
    Coupon.Featched -> showFetched()
    Coupon.Used -> showUsed()
    else -> showNotFetched()
}

fun showUsed() {}
fun showFetched() {}
fun showExpired() {}
fun showNotFetched() {}
fun showUnAvilable() {}

fun main() {
    val coupon = Coupon.GiftCoupon(1111L, Coupon.CashType, "gift")
    showStatus(coupon, User())
}