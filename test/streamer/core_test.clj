(ns streamer.core-test
  (:require [clojure.test :refer :all]
            [streamer.core :refer :all]
            [net.cgrand.xforms :as x]))

(deftest tests
  (is (= (filter odd? (range 10))
         (=> (range 10)
             (filter odd?)
             (sequence %xform %coll)))
      "`%xform` and `%coll` are accessible in last form")

  (is (= (->> (range 10)
              (filter odd?)
              (map inc))

         (=> (range 10)
             (filter odd?)
             (map inc)
             (seq!)))
      "`seq!` translates to `sequence`")

  (is (= (->> (range 10)
              (filter even?)
              (map #(Math/sqrt %))
              (reduce +))

         (=> (range 10)
             (filter even?)
             (map #(Math/sqrt %))
             (transduce! +)))
      "`transduce!` translates to `transduce`")

  (is (= (->> (range 10)
              (partition 2)
              (map vec)
              (into {}))

         (=> (range 10)
             (x/partition 2)
             (map vec)
             (into! {})))
      "`into!` translates to `into`"))
