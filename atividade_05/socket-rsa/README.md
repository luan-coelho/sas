# Tutorial de Comunicação Segura entre Bob e Alice

Este tutorial demonstra a implementação de um sistema de comunicação seguro entre dois participantes, Bob e Alice,
utilizando Java para a criação de sockets e criptografia RSA para proteger as mensagens trocadas. A criptografia e
descriptografia das mensagens são realizadas por meio de chamadas a comandos OpenSSL no terminal, utilizando a
classe `ProcessBuilder` do Java.

## Pré-requisitos

- Java JDK 8 ou superior instalado e configurado no seu sistema.
- OpenSSL instalado no seu sistema. Este tutorial foi testado com OpenSSL 3.2.1, versões anteriores deve funcionar
  também.

```shell
  $ openssl --version
  OpenSSL 3.2.1 30 Jan 2024 (Library: OpenSSL 3.2.1 30 Jan 2024)
```

## Geração das Chaves RSA

Antes de iniciar a comunicação, é necessário gerar pares de chaves RSA (pública e privada) para Bob e Alice. Isso é
feito ao executar o programa `RSAKeyPairGenerator.java`.

## Estrutura do Projeto

O projeto é dividido em três classes principais:

- `RSAKeyPairGenerator.java`: Responsável pela geração das chaves RSA para Bob e Alice e oferece métodos para
  criptografar e descriptografar mensagens.
- `AliceServer.java`: Implementa o servidor (Alice) que espera por conexões e troca mensagens com Bob.
- `BobClient.java`: Implementa o cliente (Bob) que se conecta ao servidor (Alice) e troca mensagens com ela.

## Passo a Passo da Implementação

### 1. Geração das Chaves RSA

Execute a classe `RSAKeyPairGenerator.java` para gerar as chaves. Quatro arquivos de chave serão criados na raiz do projeto:

- `bob_private_key.pem` e `bob_public_key.pem`: Chaves privada e pública de Bob, respectivamente.
- `alice_private_key.pem` e `alice_public_key.pem`: Chaves privada e pública de Alice, respectivamente.

### 2. Iniciando o Servidor (Alice)

Execute a classe `AliceServer.java` para iniciar o servidor. Alice estará escutando conexões na porta 5000.

### 3. Conectando com o Cliente (Bob)

Com o servidor em execução, inicie a classe `BobClient.java` para conectar Bob ao servidor de Alice.

### 4. Troca de Mensagens

Com a conexão estabelecida, Bob e Alice podem trocar mensagens criptografadas. Digite uma mensagem no terminal de Bob
para enviá-la a Alice. Alice pode responder, e a comunicação segue de forma bidirecional até que uma das partes envie a
mensagem "tchau", encerrando a conexão.

## Criptografia e Descriptografia

As mensagens são criptografadas no lado do emissor usando a chave pública do receptor e descriptografadas no lado do
receptor usando sua própria chave privada. Isso garante que apenas o receptor possa ler a mensagem original.

A seguir estão os comandos utilizados para gerar as chaves RSA privadas e públicas para os usuários Bob e Alice, utilizando a ferramenta OpenSSL. Estes comandos fazem parte do processo de preparação para uma comunicação segura entre eles.

### Geração da Chave Privada de Bob

```shell
openssl genpkey -algorithm RSA -out bob_private_key.pem -pkeyopt rsa_keygen_bits:1024
```

- `openssl`: Invoca a ferramenta de linha de comando OpenSSL, que é usada para criar e gerenciar certificados SSL, chaves privadas e públicas e outros componentes de criptografia.
- `genpkey`: Este subcomando é usado para gerar uma chave privada.
- `-algorithm RSA`: Especifica que o algoritmo a ser usado para gerar a chave é o RSA.
- `-out bob_private_key.pem`: Indica o nome do arquivo de saída onde a chave privada gerada será salva. Neste caso, a chave privada de Bob é salva no arquivo `bob_private_key.pem`.
- `-pkeyopt rsa_keygen_bits:1024`: Define uma opção específica para a geração da chave. Aqui, especifica-se que a chave RSA gerada deve ter um tamanho de 1024 bits.

### Extração da Chave Pública de Bob a Partir da Chave Privada

```shell
openssl rsa -pubout -in bob_private_key.pem -out bob_public_key.pem
```

- `openssl rsa`: Invoca a ferramenta OpenSSL com o subcomando `rsa`, que é usado para gerenciar chaves RSA.
- `-pubout`: Indica que a operação desejada é extrair a chave pública.
- `-in bob_private_key.pem`: Especifica o arquivo de entrada que contém a chave privada de Bob.
- `-out bob_public_key.pem`: Define o nome do arquivo de saída onde a chave pública extraída será salva. Neste caso, é `bob_public_key.pem`.

### Geração da Chave Privada de Alice

```shell
openssl genpkey -algorithm RSA -out alice_private_key.pem -pkeyopt rsa_keygen_bits:1024
```

Este comando é idêntico ao primeiro comando, mas é usado para gerar a chave privada de Alice, salvando-a no arquivo `alice_private_key.pem`.

### Extração da Chave Pública de Alice a Partir da Chave Privada

```shell
openssl rsa -pubout -in alice_private_key.pem -out alice_public_key.pem
```

Este comando segue a mesma lógica do segundo comando, mas aplica-se à chave privada de Alice. Extrai a chave pública de Alice a partir de sua chave privada e salva no arquivo `alice_public_key.pem`.

