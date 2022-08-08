package com.kent.android.slim.sample.tdd

import io.mockk.*
import org.junit.Assert
import org.junit.Test

/**
 * Created by songzhukai on 21/01/2021.
 */
class UtilTest {
    @Test
    fun ok() {
        // Given
        val util = Util()
        mockkStatic(UtilJava::class)
        mockkStatic(UtilKotlin::class)
        mockkObject(UtilCompanion.Companion)
        mockkObject(UtilSingleton)
//        val mock1 = mockkClass(Normal::class)


        every { UtilJava.ok() } returns "Joe"
        every { UtilKotlin.ok() } returns "Tsai"
        every { UtilCompanion.ok() } returns "Kent"
        every { UtilSingleton.ok() } returns "John"
//        every { mock1.ok() } returns "Tom"

        // When
        util.ok()

        // Then
        verify { UtilJava.ok() }
        verify { UtilKotlin.ok() }
        verify { UtilCompanion.ok() }
        verify { UtilSingleton.ok() }
//        verify { mock1.ok() }

        Assert.assertEquals("Joe", UtilJava.ok())
        Assert.assertEquals("Tsai", UtilKotlin.ok())
        Assert.assertEquals("Kent", UtilCompanion.ok())
        Assert.assertEquals("John", UtilSingleton.ok())
    }

    @Test
    fun ok2() {
        mockkConstructor(Normal::class)
        every { anyConstructed<Normal>().ok() } returns "Kent"

        val util = Util()
        util.ok()
        verify { anyConstructed<Normal>().ok() }

        Assert.assertEquals("Kent", Normal().ok())
    }

    @Test
    fun version_compare() {
        // give
        val util = Util()
        Assert.assertTrue(util.compareVersion("2.111.0.0", "2.111.0.0"))
        Assert.assertTrue(util.compareVersion("2.112.0.0", "2.111.0.0"))
        Assert.assertTrue(util.compareVersion("2.112.0.900", "2.111.0.0"))
        Assert.assertTrue(util.compareVersion("3.1.0.0", "2.111.0.0"))
        Assert.assertFalse(util.compareVersion("2.100.0.0", "2.111.0.0"))
        Assert.assertFalse(util.compareVersion("2.109.1.0", "2.111.0.0"))
        Assert.assertFalse(util.compareVersion("1.100.0.0", "2.111.0.0"))
        Assert.assertFalse(util.compareVersion("2.100.0.0", ""))
        Assert.assertFalse(util.compareVersion("2.100.0.0", "2.100"))
        Assert.assertTrue(util.compareVersion("2.100.0.0", "2.99.0.0"))
    }
}