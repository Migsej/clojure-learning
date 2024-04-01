(ns hello-world.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]))

(defroutes app-routes
  (GET "/" [] "Hello World <br>
       <a href=\"/list\">hehe</a><br>
       <a href=\"/integers\">integers")

  (GET "/list" [] " <form method=\"post\">
       <label for=\"items\">List items:</label><br>
       <textarea name=\"items\" rows=\"10\"></textarea><br>
       <input type=\"submit\" value=\"Randomize\"> 
       </form>")
  (POST "/list" [items] 
        (->> items
             (clojure.string/split-lines)
             (shuffle)
             (clojure.string/join "<br>")))

  (GET "/integers" [] 
       "<form method=\"post\">
       <label for=\"min\">Min:</label>
       <input type=\"text\" name=\"min\"><br><br>
       <label for=\"max\">Max:</label>
       <input type=\"text\" name=\"max\"><br><br>
       <label for=\"num\">Num:</label>
       <input type=\"text\" name=\"num\"><br><br>
       <input type=\"submit\" value=\"Get integers\">
       </form>")
  (POST "/integers" [min max num] 
        (if (some nil? [min max num])
          "Please provide the numbies!! D:"
          (let [minn (read-string min)
                maxn (read-string max)
                numn (read-string num)]
            (->> #(+ minn (rand-int (- maxn minn)))
                 (repeatedly)
                 (take numn)
                 (clojure.string/join "<br>")))))

  (GET "/hehe" request
       (let [headers (get request :headers)] 
         (str (get headers "user-agent"))))

  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes api-defaults))
