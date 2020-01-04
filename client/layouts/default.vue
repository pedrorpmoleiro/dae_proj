<template>
  <v-app dark>
    <v-navigation-drawer
      v-model="drawer"
      :mini-variant="miniVariant"
      :clipped="clipped"
      fixed
      app
    >
      <v-list>
        <v-list-item
          v-for="(item, i) in items"
          :key="i"
          :to="item.to"
          router
          exact
        >
          <v-list-item-content>
            <v-list-item-title v-text="item.title" />
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <v-app-bar
      :clipped-left="clipped"
      fixed
      app
    >
      <v-app-bar-nav-icon @click.stop="drawer = !drawer" />
      <v-toolbar-title v-text="title" />
    </v-app-bar>
    <v-content>
      <v-container>
        <nuxt />
      </v-container>
    </v-content>
    <v-footer
      :fixed="fixed"
      app
    >
      <span>&copy; 2020</span>
    </v-footer>
  </v-app>
</template>

<script>
export default {
  data() {
    return {
      clipped: false,
      drawer: false,
      fixed: false,
      items: [
        {
          title: 'Home',
          to: '/'
        },
        {
          title: 'Pagamentos',
          to: '/pagamentos'
        },
        {
          title: 'Produtos',
          to: '/produtos'
        },
        {
          title: 'Utilizadores',
          to: '/utilizadores'
        },
        {
          title: 'Modalidades',
          to: '/modalidades'
        },
        {
          title: 'Assiduidade',
          to: '/assiduidade'
        },
        {
          title: 'Login',
          to: '/login'
        },
        {
          title: 'Perfil',
          to: '/profile'
        }
      ],
      miniVariant: false,
      right: true,
      rightDrawer: false,
      title: 'Apoio à gestão de clubes desportivos'
    }
  }
}
</script>
