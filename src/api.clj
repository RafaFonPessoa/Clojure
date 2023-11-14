(ns api
  (:require [clj-http.client :as http-client]
  [cheshire.core :as json])
  )
(def api-url "https://brapi.dev/api/quote/")
(def chave "h6PQv7nGo28jwg7UiePTEU")

;; Átomo para armazenar as transações
(def transacoes (atom {:compra [] :venda []}))

(defn obter-cotacao [codigo]
  (let [url (str api-url codigo "?token=" chave)
        response (http-client/get url)]
    (if (= 200 (:status response))
      (json/parse-string (:body response) true)
      (do
        (println (str "Erro na requisição: " (:status response)))
        nil))))

(defn exibir-lista-companhias []
  (let [url (str api-url "list?token=" chave)
        response (http-client/get url)]
    (if (= 200 (:status response))
      (do
        (println "Lista de Companhias Cotadas:")
        (let [data (json/parse-string (:body response) true)
              indexes (get-in data [:indexes])
              stocks (get-in data [:stocks])]
          (doseq [index indexes]
            (println (str (:stock index) " - " (:name index))))
          (doseq [stock stocks]
            (println (str (:stock stock) " - " (:name stock)))))
        )
      (println (str "Erro na requisição: " (:status response))))))

(defn registrar-transacao [tipo codigo quantidade valor]
  (swap! transacoes update-in [tipo] conj {:codigo codigo :quantidade quantidade :valor valor :data (java.util.Date.)}))

(defn exibir-transacoes-tipo [tipo]
  (let [transacoes-tipo (get @transacoes tipo)]
    (doseq [transacao transacoes-tipo]
      (println (str "Tipo: " tipo ", Código: " (:codigo transacao) ", Quantidade: " (:quantidade transacao) ", Valor: " (:valor transacao) ", Data: " (:data transacao))))))

(defn exibir-saldo-total []
  (let [compra (get @transacoes :compra)
        venda (get @transacoes :venda)]
    (println (str "Saldo Total da Carteira: " (- (apply + (map :valor compra)) (apply + (map :valor venda)))))))

(defn exibir-dados-acao [codigo]
  (let [cotacao (obter-cotacao codigo)]
    (if cotacao
      (let [results (:results cotacao)]
        (if (seq results)
          (do
            (println (str "Dados da Ação " codigo ":"))
            (println (str "Nome: " (:longName (first results))))
            (println (str "Código: " (:symbol (first results))))
            (println (str "Tipo de Ativo: " (:currency (first results))))
            (println (str "Descrição: " (:shortName (first results))))
            (println (str "Variação do Dia (R$): " (:regularMarketChange (first results))))
            (println (str "Variação do Dia (%): " (:regularMarketChangePercent (first results))))
            (println (str "Último Preço: " (:regularMarketPrice (first results))))
            (println (str "Preço Máximo: " (:regularMarketDayHigh (first results))))
            (println (str "Preço Mínimo: " (:regularMarketDayLow (first results))))
            (println (str "Preço de Abertura: " (:regularMarketOpen (first results))))
            (println (str "Preço de Fechamento: " (:regularMarketPreviousClose (first results))))
            (println (str "Hora: " (:regularMarketTime (first results))))

            ;; Solicitar dados para registrar uma transação
            (println "Digite a quantidade de ações:")
            (let [quantidade (Integer. (read-line))]
              (println "Digite o valor da ação:")
              (let [valor (Float. (read-line))]
                (println "Digite o tipo da transação (compra/venda):")
                (let [tipo (read-line)]
                  (registrar-transacao tipo codigo quantidade valor)))))

          (do
            (println "Ação encontrada, mas sem resultados.")
            (exibir-saldo-total)))
        )
      (println (str "Ação não encontrada: " codigo)))))