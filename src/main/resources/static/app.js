const state = {
  user: null,
  token: null,
};

const api = {
  get: (path) => fetch(path).then((r) => r.json()),
  post: (path, body) => fetch(path, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(body),
  }).then((r) => r.json()),
};

function trackCard(track) {
  return `<div class="card" data-track='${JSON.stringify(track)}'>
      <h4>${track.title}</h4>
      <p>${track.artist} · ${track.album}</p>
      <p>${track.genre}</p>
    </div>`;
}

async function loadTrending() {
  const tracks = await api.get('/api/browse/trending');
  document.getElementById('trendingTracks').innerHTML = tracks.map(trackCard).join('');
  bindPlayTrack();
}

async function loadPlaylists(owner = '') {
  const query = owner ? `?owner=${encodeURIComponent(owner)}` : '';
  const playlists = await api.get(`/api/playlists${query}`);
  document.getElementById('playlists').innerHTML = playlists
    .map((p) => `<div class="row"><strong>${p.name}</strong><p>${p.description} · ${p.trackIds.length} tracks</p></div>`)
    .join('');
}

async function loadRecommendations() {
  if (!state.user) {
    document.getElementById('recommendedTracks').innerHTML = '<p>Login to view personalized recommendations.</p>';
    return;
  }
  const data = await api.get(`/api/recommendations/${state.user.id}`);
  document.getElementById('recommendedTracks').innerHTML = data.tracks.map(trackCard).join('');
  bindPlayTrack();
}

function bindPlayTrack() {
  document.querySelectorAll('.card').forEach((card) => {
    card.addEventListener('click', () => {
      const track = JSON.parse(card.dataset.track);
      document.getElementById('nowPlaying').textContent = track.title;
      document.getElementById('nowPlayingMeta').textContent = `${track.artist} · ${track.album}`;
    });
  });
}

async function searchTracks(query) {
  if (!query) {
    document.getElementById('searchResults').innerHTML = '<p>Type to search songs, artists, albums, genres.</p>';
    return;
  }
  const tracks = await api.get(`/api/tracks?q=${encodeURIComponent(query)}`);
  document.getElementById('searchResults').innerHTML = tracks.map(trackCard).join('') || '<p>No results.</p>';
  bindPlayTrack();
}

function setupNavigation() {
  document.querySelectorAll('.nav-item').forEach((button) => {
    button.addEventListener('click', () => {
      document.querySelectorAll('.nav-item').forEach((b) => b.classList.remove('active'));
      document.querySelectorAll('.view').forEach((v) => v.classList.remove('active'));
      button.classList.add('active');
      document.getElementById(button.dataset.view).classList.add('active');
    });
  });
}

async function loginDemo() {
  const response = await api.post('/api/auth/login', { username: 'demo', password: 'demo' });
  state.user = response.user;
  state.token = response.token;
  document.getElementById('userBadge').textContent = `@${state.user.username}`;
  await loadPlaylists(state.user.username);
  await loadRecommendations();
}

async function createPlaylist() {
  const name = document.getElementById('playlistName').value.trim();
  if (!name) return;
  await api.post('/api/playlists', {
    name,
    description: 'Created from SpotifyClone UI',
    owner: state.user?.username || 'demo',
    collaborative: true,
    trackIds: ['t1', 't3', 't8'],
  });
  await loadPlaylists(state.user?.username || 'demo');
  document.getElementById('playlistName').value = '';
}

function registerEvents() {
  document.getElementById('searchInput').addEventListener('input', (e) => searchTracks(e.target.value));
  document.getElementById('loginBtn').addEventListener('click', loginDemo);
  document.getElementById('createPlaylist').addEventListener('click', createPlaylist);
}

async function bootstrap() {
  setupNavigation();
  registerEvents();
  await loadTrending();
  await loadPlaylists();
  await searchTracks('');
}

bootstrap();
