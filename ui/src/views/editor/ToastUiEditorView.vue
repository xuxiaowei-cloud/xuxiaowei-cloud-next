<template>
  <div id="editor"></div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import Editor from '@toast-ui/editor'
import '@toast-ui/editor/dist/toastui-editor.css' // Editor's Style

onMounted(() => {
  const querySelectorEditor = document.querySelector('#editor')
  if (querySelectorEditor instanceof HTMLElement) {
    const editor = new Editor({
      el: querySelectorEditor,
      height: '500px',
      initialEditType: 'markdown',
      previewStyle: 'vertical'
    })

    editor.getMarkdown()
  }
})

</script>

<style scoped>

</style>
