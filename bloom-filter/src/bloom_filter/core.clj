(ns bloom-filter.core)


(defn bloom-create [numbits hash-functions]
      (assert (number? numbits) "numbits must be numeric")
      (assert (not (nil? hash-functions)) "hash-functions must not be nil")
      (assert (every? ifn? hash-functions) "hash-functions must be a collection of functions")
      {:bits (vec (repeat numbits false)) 
       :hash-functions hash-functions})

(def ^:private safe-hash-functions (memoize 
      (fn [numbits hash-functions]
      (map #(fn [x] (mod (% x) numbits)) hash-functions))))

(defn bloom-add [{:keys [hash-functions bits] :as bloom-filter} value]
      (when bloom-filter 
      (let [hash-functions (safe-hash-functions (count bits) hash-functions)]
      (->> hash-functions
           (reduce (fn [actual-bits hash-function] (assoc! actual-bits (hash-function value) true)) (transient bits))
           (persistent!)
           (assoc bloom-filter :bits)))))

(defn bloom-contains? [bloom-filter value] 
      (let [bits (:bits bloom-filter)
            hash-functions (safe-hash-functions (count bits) (:hash-functions bloom-filter))]
      (reduce (fn [actual-value hash-function] 
                  (let [new-value (and actual-value (bits (hash-function value)))]
                  (or new-value (reduced false)))) 
              true
              hash-functions)))

