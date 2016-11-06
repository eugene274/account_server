# zagar_io

## HIBERNATE
H2 in-memo db for tests 

@see server.database
@see hibernate.cfg.xml
@see server.model.dao

## RESPONSE FORMAT
Jackson API

@see server.model.customer
@see server.model.mixin

## Authenticated users

server.model.TokenService provides \<Token,Profile\> Map and some handy methods
server.model.AccountService provides all accounting stuff (email, logout, etc)
