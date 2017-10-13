import org.sonatype.nexus.ldap.persist.*
import org.sonatype.nexus.ldap.persist.entity.*
import groovy.json.JsonSlurper

def ldap = new JsonSlurper().parseText(args)
def manager = container.lookup(LdapConfigurationManager.class.name)

manager.addLdapServerConfiguration(
  new LdapConfiguration(
    name: ldap.name,
    connection: new Connection(
      host: new Connection.Host(Connection.Protocol.ldap, ldap.host, ldap.port),
      maxIncidentsCount: 3,
      connectionRetryDelay: 300,
      connectionTimeout: 15,
      searchBase: 'dc=example,dc=com',
      authScheme: 'simple',
      systemPassword: 'systemPassword',
      systemUsername: 'systemUsername'
    ),
    mapping: new Mapping(
      ldapGroupsAsRoles: true,
      emailAddressAttribute: 'mail',
      userIdAttribute: 'sAMAccountName',
      userMemberOfAttribute: 'memberOf',
      userObjectClass: 'user',
      userPasswordAttribute: 'userPassword',
      userRealNameAttribute: 'cn',
      userBaseDn: '(memberof:1.2.840.113556.1.4.1941:=cn=Devs,ou=someOU,ou=anotherOU,dc=example,dc=com'
    )
  )
)
