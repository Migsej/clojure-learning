(ns hello-world.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [hello-world.handler :refer :all]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))))

  (testing "integers"
    (let [response (app (-> (mock/request :post "/integers")
                            (mock/query-string {:min "1" :max "3" :num "89"})))
          numbers (map read-string (clojure.string/split (:body response) #"<br>"))]
      (is (= (:status response) 200))
      (is (every? #(and (>= % 1) (< % 3)) numbers))
      (is (= (count numbers) 89))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
