# zagar_io

## HIBERNATE
H2 in-memo db for tests 

@see server.dao.database
@see hibernate.cfg.xml
@see server.dao

## RESPONSE FORMAT
Jackson API

@see server.model.response
@see server.model.mixin

## Authenticated users

server.services.TokenService provides \<Token,Profile\> Map and some handy methods
server.services.AccountService provides all accounting stuff (email, logout, etc)
