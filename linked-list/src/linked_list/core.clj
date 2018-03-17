(ns linked-list.core)

(defrecord Node [value, next-node])

(defn- new-node [value]
      (->Node value nil))

(defn create-linked-list [] nil)

(defn add-to-linked-list [node new-value] 
      (if node
        (update node :next-node #(add-to-linked-list % new-value))
        (new-node new-value)))

(defn contains-linked-list? [{:keys [value next-node] :as node} query-value]
      (if (nil? node)
          false
          (or (= value query-value) (recur (:next-node node) query-value))))

(defn get-nth-linked-list [{:keys [value next-node] :as node} n]
      (if (< n 1)
          value
          (recur next-node (dec n))))

(defn without-element-linked-list [{:keys [value next-node] :as node} n]
      (cond
            (nil? node) nil
            (zero? n) next-node
            :else (update node :next-node without-element-linked-list (dec n))))
