(ns jry.test.core
  (:use jry.core expectations))

(expect fn? (% identity 1))
(expect 1 ((% identity 1)))

(expect 1 (returning [x 1] (* 2 x) (* 4 x)))

(expect true (truthy? "BRN"))
(expect true (truthy? true))
(expect true (truthy? 1))
(expect false (truthy? nil))
(expect false (truthy? false))

(expect false (falsey? "BRN"))
(expect false (falsey? true))
(expect false (falsey? 1))
(expect true (falsey? nil))
(expect true (falsey? false))

(expect [1 2 3] (every identity [1 2 3]))
(expect nil (every identity [1 2 nil]))

(expect true? (submap {:a 1 :b {:c 2}} {:a 1 :b {:c 2 :d 3} :e 4}))
(expect false? (submap {:a 1 :b {:c 2}} {:a "9" :b {:c 2 :d 3} :e 4}))
(expect false? (submap {:a 1 :b {:c 2}} {:a 1 :b {:c "9" :d 3} :e 4}))
