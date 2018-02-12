(ns bloom-filter.core)

(defn- fn->post [fvar]
      (-> fvar meta :arglists first meta :post))

(defn- hash-post-fn-impl [numbits]
      (fn [n] (<= 0 n (dec numbits))))

(def hash-post-fn (memoize hash-post-fn-impl))

(defn- valid-post? [numbits hash-fn]
      (some #(= (eval (first %)) (hash-post-fn numbits)) (fn->post hash-fn)))

(defn bloom-create [numbits hash-functions]
      (assert (number? numbits) "numbits must be numeric")
      (assert (not (nil? hash-functions)) "hash-functions must not be nil")
      (assert (every? ifn? hash-functions) "hash-functions must be a collection of functions")
      (assert (every? #(valid-post? numbits %) hash-functions) "each hash-function should have a hash-post-fn post-condition")
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

(defn bloom-contains? [{:keys [hash-functions bits] :as bloom-filter} value] 
      (when bloom-filter
      (let [hash-functions (safe-hash-functions (count bits) hash-functions)]
      (every? #(bits (% value)) hash-functions))))

