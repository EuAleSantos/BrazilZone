 # Sincronizacao_entre_Threads
Avaliação de Produto 2, Matéria: Elaboração e Montagem de Infraestrutura de Servidores...Avaliação de Produto 2, Matéria: Elaboração e Montagem de Infraestrutura de Servidores...

 # Mode de Execução:
Desenvolvemos a aplicação completa utilizando a stack tecnológica estudada ao longo do semestre, integrando conhecimentos de AWS SQS, AWS S3, Docker, Docker-Compose, Java, Spring-Boot, rabbit, Git, GitHub.

# Descrição do Trabalho:
Vocês deverão desenvolver uma aplicação web para gerenciamento de tarefas (To-Do List) que permitirá aos usuários criar, atualizar, visualizar e deletar tarefas. A aplicação deve contemplar as seguintes funcionalidades e tecnologias:

    Backend (Tecnologia a critério do aluno):

  # Implementar um serviço RESTful para gerenciar as tarefas (CRUD).
 > Incluir tarefa;
 > Apagar tarefa;
 > Consultar lista de tarefas;
 > Atualizar tarefa;
 > Persistir os dados das tarefas em um banco de dados (pode ser o AWS DynamoDB ou algum escolhido por vocês)
 > Utilizar AWS SQS ou outra mensageria para envio de notificações assíncronas quando uma tarefa for criada ou atualizada. A notificação vai conter o seguinte payload

{
    "descricao": <DESCRICAO DA TAREFA>, 
    "id": <ID DA TAREFA> 
}
 # Armazenamento de arquivos:
 > O APP deve permitir upload de arquivos relacionados às tarefas, armazenando-os no AWS S3.

 # Containerização:
 > Containerizar a aplicação backend utilizando Docker.
 > Utilizar Docker-Compose para orquestrar o backend e outros serviços necessários.

 # Deploy na Nuvem:
 > Realizar o deploy da aplicação em uma instância EC2 da AWS.
 > Configurar o ambiente de produção usando Docker-Compose.

 > trole de Versão e Integração Contínua:
 >  Utilizar Git para controle de versão do código.
 > Hospedar o repositório no GitHub.
 # Configurar GitHub Actions para realizar
 > a integração contínua,
 > executando testes automatizados
 > e realizando o deploy automático para a AWS EC2.

  #Requisitos Específicos:
 ## API RESTful:

 > Endpoints para criar, ler, atualizar e deletar tarefas.
 > Endpoint para upload de arquivos relacionados às tarefas.
 > Documentação da API utilizando Swagger ou similar.
 ## Mensageria:

 > Configuração de uma fila SQS ou outra mensageria para envio de notificações assíncronas. Eu recomendo SQS que é mais fácil.
 > Envio de mensagens para a fila sempre que uma tarefa for criada ou atualizada.
 
 ## AWS S3:
 > Configuração de um bucket S3 para armazenamento de arquivos.
 > Endpoint para upload e download de arquivos relacionados às tarefas.

 ## Banco de dados:
 > Configuração de um banco de dados. Eu recomendo tabela DynamoDB para armazenamento das tarefas.

## Docker e Docker-Compose:
 > Dockerfile para a aplicação backend.
 > docker-compose.yml para orquestração dos serviços necessários.

 ## Deploy:
 > Deploy da aplicação em uma instância EC2 da AWS.

 ## Git e GitHub Actions:
 > Repositório Git hospedado no GitHub.
 > Pipeline de integração contínua configurado com GitHub Actions.
