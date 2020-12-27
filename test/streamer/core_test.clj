(ns streamer.core-test
  (:require [clojure.test :refer :all]
            [streamer.core :refer :all]
            [net.cgrand.xforms :as x]))

(deftest tests
  (testing "anaphoric macro bindings"
    (is (= (filter odd? (range 10))
           (=> (range 10)
               (filter odd?)
               (sequence %xform %coll)))
        "`%xform` and `%coll` are accessible in last form"))

  (testing "sequence!"
    (is (= (->> (range 10)
                (filter odd?)
                (map inc))

           (=> (range 10)
               (filter odd?)
               (map inc)
               (sequence!)))
        "`sequence!` translates to `sequence`"))

  (testing "transduce!"
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
                (filter even?)
                (map #(Math/sqrt %))
                (reduce + 5))

           (=> (range 10)
               (filter even?)
               (map #(Math/sqrt %))
               (transduce! + 5)))
        "`transduce!` translates to `transduce`"))

  (testing "into!"
    (is (= (->> (range 10)
                (partition 2)
                (map vec)
                (into {}))

           (=> (range 10)
               (x/partition 2)
               (map vec)
               (into! {})))
        "`into!` translates to `into`"))

  (testing "eduction!"
    (is (= (seq (eduction
                 (map inc)
                 (filter odd?)
                 (range 10)))

           (seq (eduction
                 (comp
                  (map inc)
                  (filter odd?))
                 (range 10)))

           (seq (=> (range 10)
                    (map inc)
                    (filter odd?)
                    (eduction!)))))))
