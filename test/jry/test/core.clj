(ns jry.test.core
  (:use jry.core expectations))

(expect fn? (% identity 1))
(expect 1 ((% identity 1)))
