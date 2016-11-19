# zagar_io

## HIBERNATE
H2 in-memo db for tests 

@see server.model.dao.database
@see hibernate.cfg.xml
@see server.model.dao

## RESPONSE FORMAT
Jackson API

@see server.model.customer
@see server.model.mixin

## Authenticated users

server.model.services.TokenService provides \<Token,Profile\> Map and some handy methods
server.model.services.AccountService provides all accounting stuff (email, logout, etc)
