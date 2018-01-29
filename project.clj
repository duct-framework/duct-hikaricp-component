(defproject duct/hikaricp-component "0.1.2"
  :description "A component for managing a HikariCP connection pool"
  :url "https://github.com/weavejester/duct-hikaricp-component"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [com.stuartsierra/component "0.3.2"]
                 [com.zaxxer/HikariCP-java6 "2.3.13"]
                 [org.slf4j/slf4j-nop "1.7.25"]]
  :profiles
  {:dev {:dependencies [[org.clojure/java.jdbc "0.7.5"]
                        [com.h2database/h2 "1.4.196"]]}})
