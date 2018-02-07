(ns bloom-filter.core)


(defn bloom-create [numbits hash-functions]
      (if (or (nil? numbits) (nil? hash-functions)) nil
          {:bits (vec (repeat numbits 0)) :hash-functions hash-functions}))

(defn bloom-add [bloom-filter value]
      (when-not (or (nil? bloom-filter) (nil? value))
      (let [hash-functions (:hash-functions bloom-filter)
            bits           (:bits bloom-filter)
            new-bits (reduce (fn [actual-bits hash-function] (assoc actual-bits (hash-function value) 1)) bits hash-functions)]
      (assoc-in bloom-filter [:bits] new-bits))))

