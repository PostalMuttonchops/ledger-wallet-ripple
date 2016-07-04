package co.ledger.wallet.core.wallet.ethereum

/**
  *
  * Ether
  * ledger-wallet-ethereum-chrome
  *
  * Created by Pierre Pollastri on 13/06/2016.
  *
  * The MIT License (MIT)
  *
  * Copyright (c) 2016 Ledger
  *
  * Permission is hereby granted, free of charge, to any person obtaining a copy
  * of this software and associated documentation files (the "Software"), to deal
  * in the Software without restriction, including without limitation the rights
  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  * copies of the Software, and to permit persons to whom the Software is
  * furnished to do so, subject to the following conditions:
  *
  * The above copyright notice and this permission notice shall be included in all
  * copies or substantial portions of the Software.
  *
  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  * SOFTWARE.
  *
  */
class Ether(private val value: BigInt) {
  def +(value: Ether) = new Ether(this.value + value.value)
  def -(value: Ether) = new Ether(this.value - value.value)
  def /(value: Ether) = new Ether(this.value / value.value)
  def *(value: Ether) = new Ether(this.value * value.value)
  def %(value: Ether) = new Ether(this.value % value.value)
  def >(value: Ether) = this.value > value.value
  def >=(value: Ether) = this.value >= value.value
  def <(value: Ether) = this.value < value.value
  def <=(value: Ether) = this.value <= value.value

  override def toString: String = value.toString()

  def toEther: BigDecimal = BigDecimal(value.toString()) / BigDecimal(10).pow(18)
  def toBigInt = value
}

object Ether {
  val Zero = Ether(0)

  def apply(value: Int): Ether = new Ether(BigInt(value))
  def apply(value: Long): Ether = new Ether(BigInt(value))
  def apply(value: String): Ether = {
    // Parse Javascript double serialization (i.e. 1.654321e+200)
    if (value.indexOf("e+") != -1) {
      val pattern = "(\\d*\\.?\\d*)e\\+(\\d*)".r
      var r = ""
      pattern.findAllMatchIn(value).foreach({(m) =>
        r = s"${m.group(1).replace(".", "")}${"0" * m.group(2).toInt}"
      })
      new Ether(BigInt(r))
    } else {
      new Ether(BigInt(value))
    }
  }

  object Implicits {
    implicit def int2Ether(value: Int): Ether = Ether(value)
  }

}