(ns linked-list.core)

(defn create-linked-list [] {})

(defn add-to-linked-list [{:keys [value next-node] :as linked-list} new-value] 
      (let [new-node {:value new-value :next-node nil}]
      (if (= {} linked-list)
          new-node
          (let [new-tail (if (nil? next-node) new-node (add-to-linked-list next-node new-value))]
              (assoc-in linked-list [:next-node] new-tail)))))

(defn contains-linked-list? [{:keys [value next-node] :as linked-list} query-value]
      (if (empty? linked-list)
          false
          (or (= value query-value) (recur next-node query-value))))

(defn get-nth-linked-list [{:keys [value next-node] :as linked-list} n]
      (if (< n 1)
          value
          (recur next-node (dec n))))

(defn without-element-linked-list [{:keys [value next-node] :as linked-list} n]
      (loop [act-node linked-list counter 0 linked-list-accum (create-linked-list)]
          (if (nil? (:next-node act-node)) linked-list-accum
          (let [new-linked-list (if (= counter n) linked-list-accum (add-to-linked-list linked-list-accum act-node))] 
               (recur (:next-node act-node) (inc counter) new-linked-list)))))


